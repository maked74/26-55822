//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main()
{

    int nCells = 0; // количество ячеек
    int nPas = 0; // количество пассажиров, желающих сдать багаж
    int[][] pass = new int[0][]; // Массив, куда запишем время сдачи (первая колонка) и получения (вторая колонка) багажа пассажирами
    int[] cell = new int[0]; // массив ячеек, куда запишем время когда ячейка должна освободиться (есло 0, то ячейка свободна)

    try (BufferedReader br = new BufferedReader(new FileReader("26.txt")))
    {
        nCells =  Integer.parseInt(br.readLine());
        nPas = Integer.parseInt(br.readLine());

        pass = new int[nPas][2];
        cell = new int[nCells];
        Arrays.fill(cell, 0); // Заполним массив ячеек нулями (все ячейки свободны). хотя это не обязательно т.к. int по умолчанию = 0

        String line;
        int i = 0;
        while ((line = br.readLine()) != null)  // читаем файл построчно и заполняем массив
        {
            pass[i][0] = Integer.parseInt(line.split("\\s+")[0]);
            pass[i][1] = Integer.parseInt(line.split("\\s+")[1]);
            i++;
        }
    } catch (IOException e)
    {
        IO.println(e.getMessage());
    }

    // Сортируем массив по времени сдачи багажа
    int bufVal; // переменная для хранения промежуточного значения при перестановке
    for(int k1 = 0; k1 < nPas; k1++)
        for(int k2 = 0; k2 < nPas; k2++)
        {
            if (k1 != k2)
            {
                if (pass[k1][0] < pass[k2][0])
                {
                    bufVal = pass[k1][0];
                    pass[k1][0] = pass[k2][0];
                    pass[k2][0] = bufVal;

                    bufVal = pass[k1][1];
                    pass[k1][1] = pass[k2][1];
                    pass[k2][1] = bufVal;
                }
            }
        }

    //Arrays.sort(pass, Comparator.comparingInt(a -> a[0])); // Можно отсортировать с помощью лямбда-выражения

    int iCountPass = 0; // Количество пассажиров, сдавших багаж
    int numLastCell = 0; // Номер последней ячейки, куда был сдан багаж

    for(int k = 0; k < nPas; k++) // Цикл по пассажирам в порядке времени сдачи багажа
    {
        for (int j = 0; j < nCells; j++) // Цикл по ячейкам (выбор свободной ячейки)
        {
            if (cell[j] == 0) // если ячейка свободна, то кладем в неё багаж
            {
                cell[j] = pass[k][1]; // Занимаем ячейку
                numLastCell = j + 1;
                iCountPass++;
                break; // Завершаем цикл по ячейкам
            }
            else if (cell[j] < pass[k][0]) // Если ячейка числится не свободной, но время хранения завершено, то занимаем ячейку
            {
                cell[j] = pass[k][1]; // Занимаем ячейку
                numLastCell = j + 1;
                iCountPass++;
                break; // Завершаем цикл по ячейкам
            }
        }
    }

    // Выводим результат
    IO.print(iCountPass);
    IO.print(" ");
    IO.println(numLastCell);
}
