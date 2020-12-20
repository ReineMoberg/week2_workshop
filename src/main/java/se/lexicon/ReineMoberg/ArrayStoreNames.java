package se.lexicon.ReineMoberg;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayStoreNames {

    private static String[] names = new String[0];      //class variable, global in class, names array

    public static void main(String[] args) {

        /*User interface of simple name database*/

        Scanner userInput = new Scanner(System.in);     //declaring variables
        String operationType;
        boolean userContinue = true;

        System.out.println("Welcome to name database.");
        while (userContinue) {
            System.out.println("What do you want to do?");
            System.out.println("Print names 'P'\nAdd name 'A'\nRemove name 'R'\n" +
                    "Search name 'S'\nSearch by First name 'F'\nSearch by Last name 'L'\n" +
                    "Clear all names 'C'\nQuit 'Q'");
            operationType = userInput.next();                           //read input from user
            if (!operationType.equalsIgnoreCase("q")) {
                switch (operationType.toLowerCase()) {
                    case "a":
                        System.out.println("Enter full name: ");
                        userInput.nextLine();                           //dummy input to clear from previous input
                        boolean nameAdded = add(userInput.nextLine());  //call method with user input parameter
                        if (nameAdded) {
                            System.out.println("Name added.");
                        } else {
                            System.out.println("Name could not be added.");
                        }
                        System.out.println();                           //empty line
                        break;
                    case "r":
                        System.out.println("Enter name to be removed: ");
                        userInput.nextLine();                           //dummy input to clear from previous input
                        boolean nameRemoved = remove(userInput.nextLine()); //call method with user input parameter
                        if (nameRemoved) {
                            System.out.println("Name removed.");
                        } else {
                            System.out.println("Name could not be removed.");
                        }
                        System.out.println();
                        break;
                    case "s":
                        System.out.println("Enter name to search for: ");
                        userInput.nextLine();                               //dummy input to clear from previous input
                        boolean foundName = nameExists(userInput.nextLine());   //call method with user input parameter
                        if (foundName) {
                            System.out.println("Name exist in database.");
                        } else {
                            System.out.println("Name does not exist in database.");
                        }
                        System.out.println();
                        break;
                    case "f":
                        System.out.println("Enter First name: ");
                        String[] firstNames = findByFirstName(userInput.next());    //local variable with return values
                        if (firstNames.length == 0) {                               //from method call with user input
                            System.out.println("First name not found.");            //as parameter
                        } else {
                            System.out.println("Names found: ");
                            Arrays.stream(firstNames).forEach(System.out::println); //prints whole array with
                        }                                                           //each value on new line
                        System.out.println();
                        break;
                    case "l":
                        System.out.println("Enter Last name: ");
                        String[] lastNames = findByLastName(userInput.next());      //local variable with return values
                        if (lastNames.length == 0) {                                //from method call with user input
                            System.out.println("Last name not found.");             //as parameter
                        } else {
                            System.out.println("Names found: ");
                            Arrays.stream(lastNames).forEach(System.out::println);  //prints whole array with
                        }                                                           //each value on new line
                        System.out.println();
                        break;
                    case "c":
                        clear();                                //call method, no parameters or return values
                        System.out.println("Name database is cleared.");
                        System.out.println();
                        break;
                    case "p":
                        Arrays.stream(names).forEach(System.out::println);          //prints whole array with
                        System.out.println();                                       //each value on new line
                        break;
                    default:
                        System.out.println("Input is not valid.");
                        System.out.println();
                }
            } else {
                System.out.println("Ok, see you later.");       //user quits
                userContinue = false;
            }
        }
    }


    /* Add name to the array. Return true if name added, false if not added*/

    public static boolean add(String fullName) {
        if (fullName == null) {                                     //error check
            return false;
        }
        if (fullName.equalsIgnoreCase(" ")) {           //error check
            return false;
        }
        String[] returnArray = Arrays.copyOf(names, names.length + 1);  //local copy of name array with
        returnArray[returnArray.length - 1] = fullName;                           //one value added (name)
        names = returnArray;                                                      //names array equals local array
        return true;
    }


    /* Search array for full name. Return true if found, false if not found*/

    public static boolean nameExists(String fullName) {
        boolean exist = false;
        for (String name : names) {                             //searching through names array
            if (name.equalsIgnoreCase(fullName)) {
                exist = true;
                break;
            }
        }
        return exist;
    }


    /* Search array for first name. Return array containing all matches*/

    public static String[] findByFirstName(final String firstName) {
        String[] result = new String[0];                                        //local variable
        for (String fullName : names) {                                         //search through names array
            String extracted = fullName.substring(0, fullName.indexOf(" "));    //local variable, add first name
            if (extracted.equalsIgnoreCase(firstName)) {                        //if name found
                result = addStringToArray(result, fullName);                    //call method to add full name
            }                                                                   //to local variable
        }
        return result;                                                          //return all names found
    }


    /* Search array for last name. Return array containing all matches*/

    public static String[] findByLastName(final String lastName) {
        String[] result = new String[0];                                        //local variable
        for (String fullName : names) {                                         //search through names array
            String extracted = fullName.substring(fullName.indexOf(" ") + 1);   //local variable, add last name
            if (extracted.equalsIgnoreCase(lastName)) {                         //if name found
                result = addStringToArray(result, fullName);                    //call method to add full name
            }                                                                   //to local variable
        }
        return result;                                                          //return all names found
    }


    /* Add a string (a name) to an existing array. Return resulting array*/

    public static String[] addStringToArray(String[] addNameTo, String nameToAdd) {
        String[] newArray = Arrays.copyOf(addNameTo, addNameTo.length + 1); //local copy of parameter
        newArray[newArray.length - 1] = nameToAdd;                              //add a post (name) to local variable
        return newArray;                                                        //return variable
    }


    /* Remove a name from array. Return true if removed, false if not removed*/

    public static boolean remove(String fullName) {
        if (!nameExists(fullName)) {                                //call method for error check
            return false;
        }
        Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);          //for binary search to work, array have to
        int findIndex = Arrays.binarySearch(names, fullName);       //be sorted. find index were name is stored
        if (findIndex < 0) {                                        //error check
            return false;
        }
        String[] anotherArray = new String[names.length - 1];       //local variable
        int counter = 0;
        for (int i = 0; i < names.length; i++) {                    //copy all names except the name to be
            if (i == findIndex) {                                   //removed to local variable
                continue;
            }
            anotherArray[counter++] = names[i];
        }
        names = anotherArray;                                       //names array equals local array
        return true;
    }


    /* Completely empty the array*/

    public static void clear() {
        names = new String[0];
    }
}
