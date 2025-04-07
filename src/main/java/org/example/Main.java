package org.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static Random random = new Random();
    public static Scanner scanner = new Scanner(System.in);
    private static String newWordNew;
    private static int sumMistakes = 0;
    private static String[] openWord;
    private static final StringBuilder enteredLetters = new StringBuilder();


    public static void main(String[] args) throws FileNotFoundException {
        do {
            System.out.println("(Н)ачать игру или (В)ыйти");

            String startOrEnd = checkStartOrEnd();
            if(startOrEnd.equals("В"))
                return;

            startGame();

        }while (true);
    }

    // валидация ввода для start
    public static String checkStartOrEnd(){
        do {
            String startOrEnd = scanner.next().toUpperCase();
            if (!((startOrEnd.length() == 1 && startOrEnd.matches("[НВ]")))) {
                System.out.println("Введите букву Н или В");
            }else
                return startOrEnd;
        }while (true);
    }

    public static void startGame() throws FileNotFoundException {
        System.out.println("Начало игры");
        String newWord = getNewWord();
        maskWord(newWord);

        gameLoop(newWord);
    }

    // получить новое слово
    public static String getNewWord() throws FileNotFoundException {
        File file = new File("words");
        Scanner scanner = new Scanner(file);

        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        scanner.close();

        int indexOfNewWord = random.nextInt(words.size());
        String newWord = words.get(indexOfNewWord).toUpperCase();

        newWordNew = newWord;
        return newWord;
    }

    // зашифровать новое слово и вывести на экран
    public static void maskWord(String newWord) {

        String maskNewWord = "*".repeat(newWord.length());

        openWord = new String[newWord.length()];
        for (int i = 0; i < openWord.length; i++) {
            openWord[i] = "*";
        }
        System.out.println(maskNewWord);
    }

    // game
    public static void gameLoop(String newWord) {

        do {
            String newLetter = checkIsLetter();
            boolean check = checkLetterInWord(newLetter, newWord);

            countNumberMistakes(check);
            determineOccurrencesLetter(check, newLetter);

            printWord();
            printNoLetter(check);
            printImageVisel(check);

            addLetter(newLetter);

            boolean checkState = checkStateWord();

            if(checkState){
                resetLettersAndMistakes();
                System.out.println("Вы выйграли");
                System.out.println("");
                return;
            }
            if(sumMistakes == 6){
                resetLettersAndMistakes();
                System.out.println("Увы, мой друг, Вы програли");
                System.out.println("Загаданное слово: " + newWordNew);
                System.out.println("");
                return;
            }

        }while (true);
    }

    // валидация буквы
    public static String checkIsLetter() {
        System.out.println("Введите одну букву от А до Я");

        do {
            String newLetter = scanner.next().toUpperCase();
            if(!(newLetter.length() == 1 && newLetter.matches("[а-яА-Я]"))) {
                System.out.println("Введите одну букву от А до Я");
            }else if (enteredLetters.toString().contains(newLetter)) {
                System.out.println("Вы уже вводили эту букву. Введите одну букву от А до Я");
            }else
                return newLetter.toUpperCase();
        } while (true);
    }

    // запись введенных букв
    public static void addLetter(String newLetter) {
        int length = enteredLetters.length();
        if(length == 0){
            enteredLetters.append(newLetter);
            System.out.println("Вы ввели буквы: " + enteredLetters);
            System.out.println("");
        }else {
            enteredLetters.append(", ");
            enteredLetters.append(newLetter);
            System.out.println("Вы ввели буквы: " + enteredLetters);
            System.out.println("");
           }
    }

    // есть ли буква в слове
    public static boolean checkLetterInWord(String newLetter, String newWord) {
        char a = newLetter.charAt(0);
        for (char c : newWord.toCharArray()) {
            if (a == c) return true;
        }
        return false;
    }

    //такой буквы нет
    public static void printNoLetter (boolean letterInWord){
        if(!letterInWord)
            System.out.println("Такой буквы нет!");
    }

    // подсчет количества ошибок
    public static void countNumberMistakes(boolean isMistake) {
        if (!isMistake) {
            sumMistakes++;
        }
    }

    // - буквы нет в слове - печатать виселицу
    public static void printImageVisel(boolean checkLetterInWord) {
        if (!checkLetterInWord && sumMistakes == 1) {
            char[][] missOne = {{'-', '-', '-', '-', '-', ' '},
                                {'|', '/', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', 'O', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '}};
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    System.out.print(missOne[i][j]);
                }
                System.out.println("");
            }
            System.out.println("У вас осталось попыток: " + (6 - sumMistakes));
        } else if (!checkLetterInWord && sumMistakes == 2) {
            char[][] missOne = {{'-', '-', '-', '-', '-', ' '},
                                {'|', '/', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', 'O', ' '},
                                {'|', ' ', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '}};
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    System.out.print(missOne[i][j]);
                }
                System.out.println("");
            }
            System.out.println("У вас осталось попыток: " + (6 - sumMistakes));
        }else if (!checkLetterInWord && sumMistakes == 3) {
            char[][] missOne = {{'-', '-', '-', '-', '-', ' '},
                                {'|', '/', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', 'O', ' '},
                                {'|', ' ', ' ', '/', '|', ' '},
                                {'|', ' ', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '}};
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    System.out.print(missOne[i][j]);
                }
                System.out.println("");
            }
            System.out.println("У вас осталось попыток: " + (6 - sumMistakes));
        }else if (!checkLetterInWord && sumMistakes == 4) {
            char[][] missOne = {{'-', '-', '-', '-', '-', ' '},
                                {'|', '/', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', 'O', ' '},
                                {'|', ' ', ' ', '/', '|', '\\'},
                                {'|', ' ', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '}};
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 6; j++) {
                            System.out.print(missOne[i][j]);
                        }
                        System.out.println("");
                    }
            System.out.println("У вас осталось попыток: " + (6 - sumMistakes));
        }else if (!checkLetterInWord && sumMistakes == 5) {
            char[][] missOne = {{'-', '-', '-', '-', '-', ' '},
                                {'|', '/', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', 'O', ' '},
                                {'|', ' ', ' ', '/', '|', '\\'},
                                {'|', ' ', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', '/', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '}};
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 6; j++) {
                                System.out.print(missOne[i][j]);
                            }
                            System.out.println("");
                        }
                        System.out.println("У вас осталось попыток: " + (6 - sumMistakes));
        }else if (!checkLetterInWord && sumMistakes == 6) {                             // ПРОИГРАЛ !!!!!!!
            char[][] missOne = {{'-', '-', '-', '-', '-', ' '},
                                {'|', '/', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', ' ', 'O', ' '},
                                {'|', ' ', ' ', '/', '|', '\\'},
                                {'|', ' ', ' ', ' ', '|', ' '},
                                {'|', ' ', ' ', '/', ' ', '\\'},
                                {'|', ' ', ' ', ' ', ' ', ' '},
                                {'|', ' ', ' ', ' ', ' ', ' '}};
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 6; j++) {
                                System.out.print(missOne[i][j]);
                            }
                            System.out.println("");
                        }
                    }
    }

    // определить количество вхождений буквы в слово и их индекс
    public static void determineOccurrencesLetter ( boolean letterInWord, String enteredLetter){
        if (letterInWord) {
            String[] arr = newWordNew.split("");
            for (int j = 0; j < arr.length; j++) {
                if (enteredLetter.equals(arr[j]) && openWord[j].equals("*")) {
                    openWord[j] = arr[j];
                }
            }
        }
    }

    //проверка угаданно ли слово
    public static boolean checkStateWord () {
        int sum = 0;
        for (int j = 0; j < openWord.length; j++) {
            if (openWord[j].equals("*"))
                sum++;
        }
        return (sum == 0);
    }

    // печатать слово
    public static void printWord () {
        for (String x : openWord) {
            System.out.print(x);
        }
        System.out.println("");
    }

    // обнулить введенные буквы и ошибки
    public static void resetLettersAndMistakes(){
        sumMistakes = 0;
        enteredLetters.delete(0, enteredLetters.length());
    }
}





