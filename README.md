# TestTaskForGridnineSystems
Тестовое задание от Gridnine Systems

Я реализовал 3 основных метода pastFlightsFilter, incorrectSegmentsFilter и moreThenTwoHoursWaitingFilter в классе Main, которые выполняют каждый из 3-ёх необходимых фильтров из ТЗ.

Поскольку в ТЗ было сказано, что сторонних библиотек не нужно использовать, я отказался от gradle, maven, spring boot, mockito. Оставил только стандартные библиотеки и Junit 5, который можно было использовать. 

Тесты к моим методам находятся в папке com.gridnine.testing.junittests. Так как я не использовал моки, пришлось изменить в ваших классах Segment и Flight видимость конструкторов на public, чтобы создавать их объекты для тестов. Для тестов созданы новые объекты на основе Segment, Flight, чтобы проверить работу методов. Создано 4 тестовых метода, которые покрывают 3 основных метода, решающих задачу из ТЗ, и 1 вспомогательный метод с поверностным копированием коллекции.
