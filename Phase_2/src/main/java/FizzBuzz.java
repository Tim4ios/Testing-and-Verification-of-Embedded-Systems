
public class FizzBuzz {
    public String evaluate(int number) {
        return (number % 3 == 0 && number % 5 == 0)
                ? "FizzBuzz"
                : (number % 3 == 0)
                ? "Fizz"
                : (number % 5 == 0)
                ? "Buzz"
                : String.valueOf(number);

    }
}
