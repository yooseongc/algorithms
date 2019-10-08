package chap12;

import utilities.StdOut;

/**
 * An Immutable data type for dates.
 */
public class Date implements Comparable<Date> {

    private static final int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private final int year;
    private final int month;  // 1 ~ 12
    private final int day;    // 1 ~ DAYS[month]

    public Date(int year, int month, int day) {
        if (!isValid(year, month, day)) throw new IllegalArgumentException("Invalid date");
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(String date) {
        String[] fields = date.split("/");
        if (fields.length != 3) throw new IllegalArgumentException("Invalid date");
        month = Integer.parseInt(fields[0]);
        day   = Integer.parseInt(fields[1]);
        year  = Integer.parseInt(fields[2]);
        if (!isValid(year, month, day)) throw new IllegalArgumentException("Invalid date");
    }

    private boolean isValid(int year, int month, int day) {
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > DAYS[month]) return false;
        if (month == 2 && day == 29 && !isLeapYear(year)) return false;
        return true;
    }

    private boolean isLeapYear(int year) {
        if (year % 400 == 0) return true;
        if (year % 100 == 0) return false;
        return year % 4 == 0;
    }

    public int year()  { return year;  }
    public int month() { return month; }
    public int day()   { return day;   }

    public Date next() {
        if      (isValid(year, month, day + 1))      return new Date(year, month, day + 1);
        else if (isValid(year, month + 1, 1)) return new Date(year, month + 1, 1);
        else                                               return new Date(year + 1, 1, 1);
    }

    public boolean isAfter(Date that) {
        return compareTo(that) > 0;
    }

    public boolean isBefore(Date that) {
        return compareTo(that) < 0;
    }

    @Override
    public int compareTo(Date that) {
        if (this.year  < that.year)  return -1;
        if (this.year  > that.year)  return +1;
        if (this.month < that.month) return -1;
        if (this.month > that.month) return +1;
        if (this.day   < that.day)   return -1;
        if (this.day   > that.day)   return +1;
        return 0;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Date that = (Date) other;
        return (this.month == that.month) && (this.day == that.day) && (this.year == that.year);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31*hash + month;
        hash = 31*hash + day;
        hash = 31*hash + year;
        return hash;
    }

    public static void main(String[] args) {
        Date today = new Date(2004, 2, 25);
        StdOut.println(today);
        for (int i = 0; i < 10; i++) {
            today = today.next();
            StdOut.println(today);
        }
        assert today.isAfter(today.next());
        assert !today.isAfter(today.next());
        assert today.next().isAfter(today);

        Date birthday = new Date(1971, 10, 16);
        StdOut.println(birthday);
        for (int i = 0; i < 10; i++) {
            birthday = birthday.next();
            StdOut.println(birthday);
        }
    }

}
