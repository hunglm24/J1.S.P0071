import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputData {
    private final Scanner scn = new Scanner(System.in);

    public int inputInteger(String mess, int min, int max) {
        System.out.print(mess);
        while (true) {
            try {
                String input = scn.nextLine().trim();
                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.out.print("Number must be in range [" + min + "-" + max + "]. Re-enter: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Re-enter: ");
            }
        }
    }

    public String inputString(String mess, String regex) {
        System.out.print(mess);
        while (true) {
            String str = scn.nextLine().trim();
            if (!str.isEmpty() && (regex.isEmpty() || str.matches(regex))) {
                return str;
            } else {
                System.out.print("Invalid format. Re-enter: ");
            }
        }
    }

    public double inputDouble(String mess, double min, double max) {
        System.out.print(mess);
        while (true) {
            try {
                String input = scn.nextLine().trim();
                double number = Double.parseDouble(input);
                // Check multiple of 0.5 (8.0, 8.5, etc.)
                if (number >= min && number <= max && (number % 0.5 == 0)) {
                    return number;
                } else {
                    System.out.print("Value must be between " + min + " and " + max + " and multiple of 0.5. Re-enter: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Re-enter: ");
            }
        }
    }
    public String inputDate(String mess) {
        System.out.print(mess);
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
        sdf.setLenient(false); // Bắt buộc ngày phải tồn tại (VD: không chấp nhận 30-02)
        
        while (true) {
            String input = scn.nextLine().trim();
            try {
                // Bước 1: Parse thử để xem ngày có tồn tại không
                Date date = sdf.parse(input);
                
                // Bước 2: Trả về chuỗi ngày đã được chuẩn hóa (để khớp format trong Constant)
                return sdf.format(date);
            } catch (ParseException e) {
                System.out.print("Date does not exist or invalid format (" + Constant.DATE_FORMAT + "). Re-enter: ");
            }
        }
    }
}