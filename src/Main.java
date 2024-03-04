import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
  public static void main(String[] args) {
    String name = "Emmanuel";
    String[] students = {"Anna", "Bob", "Charlie", "Don", "Esther"};

    Function<String, String> uCase = String::toUpperCase;
    System.out.println(uCase.apply(name));

    Function<String, String> lastName = s -> s.concat(" Omulo");
    Function<String, String> uCaseLastName = uCase.andThen(lastName);  //? Executed after the function UCase
    System.out.println(uCaseLastName.apply(name));

    uCaseLastName = uCase.compose(lastName);  //? Executed before the function uCase
    System.out.println(uCaseLastName.apply(name));

    Function<String, String[]> func1 = uCase
        .andThen(s -> s.concat(" Omulo"))
        .andThen(s -> s.split(" "));
    System.out.println(Arrays.toString(func1.apply(name)));

    Function<String, String> func2 = uCase
        .andThen(s -> s.concat(" Omulo"))
        .andThen(s -> s.split(" "))
        .andThen(s -> s[1].toUpperCase() + ", " + s[0]);
    System.out.println(func2.apply(name));

    Function<String, Integer> func3 = uCase
        .andThen(s -> s.concat(" Omulo"))
        .andThen(s -> s.split(" "))
        .andThen(s -> s[1].toUpperCase() + ", " + s[0])
        .andThen(s -> String.join(", ", s))
        .andThen(String::length);
    System.out.println(func3.apply(name));

    Consumer<String> c1 = s -> System.out.print(s.charAt(0));
    Consumer<String> c2 = System.out::println;
    Arrays.asList(students).forEach(c1.andThen(s -> System.out.print(" - ")).andThen(c2));

    Predicate<String> p1 = s -> s.equals("EMMANUEL");
    Predicate<String> p2 = s -> s.equalsIgnoreCase("Emmanuel");
    Predicate<String> p3 = s -> s.startsWith("E");
    Predicate<String> p4 = s -> s.endsWith("s");

    Predicate<String> result1 = p1.or(p2);
    System.out.println(result1.test(name));

    Predicate<String> result2 = p1.and(p2);
    System.out.println(result2.test(name));

    Predicate<String> result3 = p3.or(p4);
    System.out.println(result3.test(name));

    Predicate<String> result4 = p3.and(p4);
    System.out.println(result4.test(name));

    Predicate<String> result5 = p3.and(p4).negate();  //? negate result of AND & OR
    System.out.println(result5.test(name));

    record Person(String firstName, String lastName) {}

    List<Person> list = new ArrayList<>();
    list.add(new Person("Patrick", "Mahomes"));
    list.add(new Person("Odell", "Beckam"));
    list.add(new Person("Tom", "Brady"));
    list.add(new Person("Joe", "Burrow"));

    list.sort((per1, per2) -> per1.lastName.compareTo(per2.lastName));
    list.forEach(System.out::println);

    System.out.println("-".repeat(50));
    list.sort(Comparator.comparing(Person::lastName));
    list.forEach(System.out::println);

    System.out.println("-".repeat(50));
    list.sort(Comparator.comparing(Person::lastName).thenComparing(Person::firstName));
    list.forEach(System.out::println);

    System.out.println("-".repeat(50));
    list.sort(Comparator.comparing(Person::lastName).thenComparing(Person::firstName).reversed());
    list.forEach(System.out::println);
  }
}
