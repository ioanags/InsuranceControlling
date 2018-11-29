import java.util.Scanner;

public class Menu {
    public static void mainMenu() {
        DbConnection connection = new DbConnection();
        connection.connection();
    int userInput;
        System.out.println("----Select functionality to perform:----");
        System.out.println("*1 Vehicle Insurance Status");
        System.out.println("*2 Forecoming expiries");
        System.out.println("*3 Expiries by plate");
        System.out.println("*4 Fine calculation");
do{
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        userInput = input;
        if (!(userInput>=1 && userInput <=4)){
            System.out.println("Please enter a number between 1-4");
        }
}while (!(userInput>=1 && userInput <=4));

            if (userInput == 1) {
                System.out.println("Enter your plate number");
                Scanner plateInput = new Scanner(System.in);
                String pattern = "[A-Z]{3}[-][0-9]{4}";
                if (plateInput.hasNext(pattern)) {
                    String plate = plateInput.next(pattern);
                    connection.searchTable(plate);


                } else {
                    System.out.println("Wrong plate format");

                }

            } else if (userInput == 2) {
                System.out.println("Enter export type");
                System.out.println("*1 Console");
                System.out.println("*2 File");
                Scanner newInput = new Scanner(System.in);
                int choice = newInput.nextInt();
                    if (choice == 1){
                        System.out.println("Enter days");
                        Scanner daysInput = new Scanner(System.in);
                        int days = daysInput.nextInt();
                        connection.aboutToExpire(days);
                    }else if (choice==2){
                        System.out.println("Enter days");
                        Scanner daysInput = new Scanner(System.in);
                        int days = daysInput.nextInt();
                        connection.aboutToExpireCSV(days);
                    }

            } else if (userInput == 3) {
                System.out.println("hello");
                connection.expiredAscendingInList();

            } else if (userInput == 4) {
                System.out.println("Selection 4");


            }

    }
}
