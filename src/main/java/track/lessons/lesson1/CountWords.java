package track.lessons.lesson1;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
/**
 * Задание 1: Реализовать два метода
 *
 * Формат файла: текстовый, на каждой его строке есть (или/или)
 * - целое число (int)
 * - текстовая строка
 * - пустая строка (пробелы)
 *
 *
 * Пример файла - words.txt в корне проекта
 *
 * ******************************************************************************************
 *  Пожалуйста, не меняйте сигнатуры методов! (название, аргументы, возвращаемое значение)
 *
 *  Можно дописывать новый код - вспомогательные методы, конструкторы, поля
 *
 * ******************************************************************************************
 *
 */


public class CountWords {

    /**
     * Метод на вход принимает объект File, изначально сумма = 0
     * Нужно пройти по всем строкам файла, и если в строке стоит целое число,
     * то надо добавить это число к сумме
     * @param file - файл с данными
     * @return - целое число - сумма всех чисел из файла
     */
    public long countNumbers(File file) throws Exception {
        long countNumber = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //чтение построчно
            String str = br.readLine();
            while (str != null) {
                if (str.matches("[0-9]+")) {
                    countNumber += Integer.parseInt(str);
                }
                str = br.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return countNumber;
    }


    /**
     * Метод на вход принимает объект File, изначально результат= ""
     * Нужно пройти по всем строкам файла, и если в строка не пустая и не число
     * то надо присоединить ее к результату через пробел
     * @param file - файл с данными
     * @return - результирующая строка
     */
    public String concatWords(File file) throws Exception {
        StringBuilder resultStr = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //чтение построчно
            String str = br.readLine();
            while (str != null) {
                if (!str.matches("[0-9]+") && !str.equals("")) {
                    resultStr.append(str + " ");
                }
                str = br.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return resultStr.toString().trim();
    }
}