public class SimpleAgeCalculator {
    public static void main(String[] args) {
        // Validate number of arguments
        if (args.length != 4) {
            System.out.println("Error: Provide exactly 4 arguments - DOB/AGE, Reference Date, Date Format, Delimiter");
            return;
        }

        // Read command-line arguments
        String dobOrAge = args[0]; // DOB= or AGE=
        String refDate = args[1];  // Reference Date
        String dateFormat = args[2]; // Date format like DDdlcMMdlcYYYY
        String delimiter = args[3];  // Delimiter like -, /, .

        // Parse reference date
        String[] refDateParts = refDate.split(delimiter);
        int refYear = 0, refMonth = 0, refDay = 0;

        if (dateFormat.equals("YYYYdlcMMdlcDD")) {
            refYear = Integer.parseInt(refDateParts[0]);
            refMonth = Integer.parseInt(refDateParts[1]);
            refDay = Integer.parseInt(refDateParts[2]);
        } else if (dateFormat.equals("DDdlcMMdlcYYYY")) {
            refDay = Integer.parseInt(refDateParts[0]);
            refMonth = Integer.parseInt(refDateParts[1]);
            refYear = Integer.parseInt(refDateParts[2]);
        } else if (dateFormat.equals("MMdlcDDdlcYYYY")) {
            refMonth = Integer.parseInt(refDateParts[0]);
            refDay = Integer.parseInt(refDateParts[1]);
            refYear = Integer.parseInt(refDateParts[2]);
        } else {
            System.out.println("Error: Unsupported date format.");
            return;
        }

        if (!isValidDate(refDay, refMonth, refYear)) {
            System.out.println("Error: Invalid reference date.");
            return;
        }

        // Process input for DOB or AGE
        if (dobOrAge.startsWith("DOB=")) {
            String dob = dobOrAge.substring(4); // Extract DOB
            calculateAge(dob, refDay, refMonth, refYear, dateFormat, delimiter);
        } else if (dobOrAge.startsWith("AGE=")) {
            String age = dobOrAge.substring(4); // Extract AGE
            calculateDOB(age, refDay, refMonth, refYear, delimiter);
        } else {
            System.out.println("Error: Input must start with 'DOB=' or 'AGE='.");
        }
    }

    // Calculate age based on DOB
    public static void calculateAge(String dob, int refDay, int refMonth, int refYear, String dateFormat, String delimiter) {
        String[] dobParts = dob.split(delimiter);
        int dobYear = 0, dobMonth = 0, dobDay = 0;

        if (dateFormat.equals("YYYYdlcMMdlcDD")) {
            dobYear = Integer.parseInt(dobParts[0]);
            dobMonth = Integer.parseInt(dobParts[1]);
            dobDay = Integer.parseInt(dobParts[2]);
        } else if (dateFormat.equals("DDdlcMMdlcYYYY")) {
            dobDay = Integer.parseInt(dobParts[0]);
            dobMonth = Integer.parseInt(dobParts[1]);
            dobYear = Integer.parseInt(dobParts[2]);
        } else if (dateFormat.equals("MMdlcDDdlcYYYY")) {
            dobMonth = Integer.parseInt(dobParts[0]);
            dobDay = Integer.parseInt(dobParts[1]);
            dobYear = Integer.parseInt(dobParts[2]);
        } else {
            System.out.println("Error: Unsupported date format.");
            return;
        }

        if (!isValidDate(dobDay, dobMonth, dobYear)) {
            System.out.println("Error: Invalid DOB.");
            return;
        }

        int ageYears = refYear - dobYear;
        int ageMonths = refMonth - dobMonth;
        int ageDays = refDay - dobDay;

        if (ageDays < 0) {
            ageDays += 30;
            ageMonths--;
        }
        if (ageMonths < 0) {
            ageMonths += 12;
            ageYears--;
        }

        System.out.println("Age is: " + ageYears + " years, " + ageMonths + " months, " + ageDays + " days");
    }

    // Calculate DOB based on age
    public static void calculateDOB(String age, int refDay, int refMonth, int refYear, String delimiter) {
        String[] ageParts = age.split(delimiter);
        int ageYears = Integer.parseInt(ageParts[0]);
        int ageMonths = Integer.parseInt(ageParts[1]);
        int ageDays = Integer.parseInt(ageParts[2]);

        int dobYear = refYear - ageYears;
        int dobMonth = refMonth - ageMonths;
        int dobDay = refDay - ageDays;

        if (dobDay <= 0) {
            dobDay += 30;
            dobMonth--;
        }
        if (dobMonth <= 0) {
            dobMonth += 12;
            dobYear--;
        }

        System.out.println("DOB is: " + dobDay + "-" + dobMonth + "-" + dobYear);
    }

    // Check if the given date is valid
    public static boolean isValidDate(int day, int month, int year) {
        int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (month < 1 || month > 12) return false;

        if (month == 2 && isLeapYear(year)) {
            return day >= 1 && day <= 29;
        } else {
            return day >= 1 && day <= daysInMonth[month - 1];
        }
    }

    // Check if a year is a leap year
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}