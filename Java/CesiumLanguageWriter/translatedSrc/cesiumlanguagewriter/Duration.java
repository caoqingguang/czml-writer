package cesiumlanguagewriter;


import agi.foundation.compatibility.*;
import agi.foundation.compatibility.annotations.CS2JInfo;
import agi.foundation.compatibility.annotations.CS2JWarning;
import agi.foundation.compatibility.CultureInfoHelper;
import agi.foundation.compatibility.IEquatable;
import agi.foundation.compatibility.ImmutableValueType;
import agi.foundation.compatibility.PrimitiveHelper;
import agi.foundation.compatibility.StringHelper;

/**
 *  
 Represents a span of time.
 
 This class
 offers the same precision as the {@link JulianDate} type.  It stores a
 number of days as an {@code int} and a number of seconds as a {@code double}.
 

 */
@SuppressWarnings("unused")
public final class Duration implements Comparable<Duration>, IEquatable<Duration>, ImmutableValueType {
    /**
    * Initializes a new instance.
    */
    public Duration() {}

    /**
    *  Gets the largest possible value of a {@link Duration}.
    
    

    * <p>
    The value is {@link Integer#MAX_VALUE} days and 86399.0 seconds.
    
    */
    public static Duration getMaxValue() {
        return s_maxValue;
    }

    /**
    *  Gets the smallest possible value of a {@link Duration}.
    
    

    * <p>
    The value is {@link Integer#MIN_VALUE} days and -86399.0 seconds.
    
    */
    public static Duration getMinValue() {
        return s_minValue;
    }

    /**
    *  Gets a {@link Duration} of 0 days and seconds.
    

    */
    public static Duration getZero() {
        return s_zero;
    }

    /**
    *  
    Converts the specified number of hours, minutes, and seconds to total duration in seconds.
    
    
    
    
    

    * @param hours The number of hours.
    * @param minutes The number of minutes.
    * @param seconds The number of seconds.
    * @return The corresponding duration in seconds.
    */
    public static double hoursMinutesSecondsToSeconds(int hours, int minutes, double seconds) {
        return hours * TimeConstants.SecondsPerHour + minutes * TimeConstants.SecondsPerMinute + seconds;
    }

    /**
    *  
    Initializes a new instance of Duration from a specified number
    of days, hours, minutes, and seconds.
    
    
    
    
    

    * @param days The number of days in the new duration.
    * @param hours The number of hours in the new duration.
    * @param minutes The number of minutes in the new duration.
    * @param seconds The number of seconds in the new duration.
    */
    public Duration(int days, int hours, int minutes, double seconds) {
        this(days, hoursMinutesSecondsToSeconds(hours, minutes, seconds));
    }

    /**
    *  
    Initializes a new instance of Duration from a specified number
    of days and seconds.
    
    
    

    * @param days The number of days in the new duration.
    * @param seconds The number of seconds in the new duration.
    */
    public Duration(int days, double seconds) {
        // Ensure that the number of seconds is less than a whole (positive or negative) day.
        if (seconds >= 86400.0 || seconds <= -86400.0) {
            int newDays = (int) (seconds / TimeConstants.SecondsPerDay);
            days += newDays;
            seconds -= TimeConstants.SecondsPerDay * newDays;
        }
        // Ensure that days and seconds are either both negative or both positive
        if (days < 0 && seconds > 0) {
            ++days;
            seconds -= TimeConstants.SecondsPerDay;
            if (seconds < -TimeConstants.NextBefore86400) {
                --days;
                seconds = 0.0;
            }
        } else if (days > 0 && seconds < 0) {
            --days;
            seconds += TimeConstants.SecondsPerDay;
            if (seconds > TimeConstants.NextBefore86400) {
                ++days;
                seconds = 0.0;
            }
        }
        m_days = days;
        m_seconds = seconds;
    }

    /**
    *  Gets the day component of this duration.
    

    */
    public final int getDays() {
        return m_days;
    }

    /**
    *  Gets the seconds component of this duration.  This is the number of seconds represented by
    this duration in addition to the {@code Days} ({@link #getDays get}), so it will always be less than
    {@link TimeConstants#SecondsPerDay}.
    

    */
    public final double getSeconds() {
        return m_seconds;
    }

    /**
    *  Gets the total number of whole and fractional days represented by this Duration.
    

    */
    public final double getTotalDays() {
        return m_days + m_seconds / TimeConstants.SecondsPerDay;
    }

    /**
    *  Gets the total number of seconds represented by this duration, including the seconds
    that compose the days returned by the {@code Days} ({@link #getDays get}) property.
    

    */
    public final double getTotalSeconds() {
        return m_days * TimeConstants.SecondsPerDay + m_seconds;
    }

    /**
    *  
    Returns the value of the {@link Duration} in {@code Days} ({@link #getDays get}):{@code Seconds} ({@link #getSeconds get}).
    
    

    * @return The string.
    */
    @Override
    public String toString() {
        return StringHelper.format(CultureInfoHelper.getCurrentCulture(), "{0}:{1}", m_days, m_seconds);
    }

    /**
    *  
    Returns a hash code for this instance, which is suitable for use in hashing algorithms and data structures like a hash table.
    
    

    * @return A hash code for the current object.
    */
    @Override
    public int hashCode() {
        return HashCode.combine(PrimitiveHelper.hashCode(m_days), PrimitiveHelper.hashCode(m_seconds));
    }

    /**
    *  
    Returns true if this Duration exactly equals another duration, within the limits
    of floating point precision.  To be considered equal, the {@code Days} ({@link #getDays get})
    property must be identical and the
    difference between the {@code Seconds} ({@link #getSeconds get}) properties must be less than 1.0e-10.
    
    
    

    * @param other The other duration.
    * @return true if this duration exactly equals the {@code other} duration, within the limits of floating point precision.
    */
    public final boolean equalsType(Duration other) {
        return m_days == other.m_days && Math.abs(m_seconds - other.m_seconds) < Constants.Epsilon10;
    }

    /**
    *  
    Returns true if this Duration exactly equals another duration, within the limits
    of floating point precision.  To be considered equal, the {@code Days} ({@link #getDays get})
    property must be identical and the
    difference between the {@code Seconds} ({@link #getSeconds get}) properties must be less than 1.0e-10.
    
    
    

    * @param obj The other duration.
    * @return true if this duration exactly equals the {@code obj} duration, within the limits of floating point precision.
    */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Duration && equalsType((Duration) obj);
    }

    /**
    *  
    Returns true if this Duration is within {@code epsilon} seconds of the
    specified Duration.  That is, in order for the Durations to be considered equal (and for
    this function to return true), the absolute value of the difference between them, in
    seconds, must be less than {@code epsilon}.
    
    
    
    

    * @param other The Duration to compare to this Duration.
    * @param epsilon The largest difference between the Durations, in seconds, such that they will be considered equal.
    * @return true if the dates are equal as defined by the epsilon value.
    */
    @CS2JWarning("Unhandled attribute removed: Pure")
    public final boolean equalsEpsilon(Duration other, double epsilon) {
        return Math.abs(subtract(other).getTotalSeconds()) <= epsilon;
    }

    /**
    *  
    Compares this instance with another instance of the same type.
    
    
    

    * @param other An object to compare with this instance.
    * @return 
    A value indicating the relative order of the objects being compared.  The return value has these meanings:
    <table border="1"><tr></tr><tr><th></th><th>Value</th><th></th><th>Meaning</th><th></th></tr><tr></tr><tr><td></td><td>Less than zero</td><td></td><td>
    This instance is less than {@code other}.
    </td><td></td></tr><tr></tr><tr><td></td><td>Zero</td><td></td><td>
    This instance is equal to {@code other}.
    </td><td></td></tr><tr></tr><tr><td></td><td>Greater than zero</td><td></td><td>
    This instance is greater than {@code other}.
    </td><td></td></tr><tr></tr></table>
    
    */
    public final int compareTo(Duration other) {
        if (m_days != other.m_days) {
            return m_days < other.m_days ? -1 : 1;
        }
        if (m_seconds == other.m_seconds) {
            return 0;
        }
        return m_seconds < other.m_seconds ? -1 : 1;
    }

    /**
    *  
    Adds the specified Duration to this instance.
    
    
    

    * @param other The duration to add to this instance.
    * @return A {@link Duration} that represents the value of this instance plus the value of {@code other}.
    */
    public final Duration add(Duration other) {
        return new Duration(m_days + other.m_days, m_seconds + other.m_seconds);
    }

    /**
    *  
    Subtracts the specified Duration from this instance.
    
    
    

    * @param other The Duration to subtract from this instance.
    * @return A Duration that represents the value of this instance minus the value of other.
    */
    public final Duration subtract(Duration other) {
        return new Duration(m_days - other.m_days, m_seconds - other.m_seconds);
    }

    /**
    *  
    Multiplies the duration by a constant.
    
    
    

    * @param constant The constant by which to multiply the Duration.
    * @return A Duration that represents the value of this instance multiplied by the constant.
    */
    @CS2JWarning("Unhandled attribute removed: Pure")
    public final Duration multiply(double constant) {
        double days = m_days * constant;
        int wholeDays = (int) days;
        double fractionOfDay = days - wholeDays;
        double seconds = fractionOfDay * TimeConstants.SecondsPerDay + m_seconds * constant;
        return new Duration(wholeDays, seconds);
    }

    /**
    *  
    Divides the duration by another duration, yield a constant.
    
    
    

    * @param divisor The duration by which to divide this duration.
    * @return The result of dividing this Duration by another.
    */
    @CS2JWarning("Unhandled attribute removed: Pure")
    public final double divide(Duration divisor) {
        return getTotalSeconds() / divisor.getTotalSeconds();
    }

    /**
    *  
    Divides the duration by a constant.
    
    
    

    * @param constant The constant by which to divide the {@link Duration}.
    * @return A {@link Duration} that represents the value of this instance divided by the constant.
    */
    @CS2JWarning("Unhandled attribute removed: Pure")
    public final Duration divide(double constant) {
        double days = m_days / constant;
        int wholeDays = (int) days;
        double fractionOfDay = days - wholeDays;
        double seconds = fractionOfDay * TimeConstants.SecondsPerDay + m_seconds / constant;
        return new Duration(wholeDays, seconds);
    }

    /**
    *  
    Adds a specified number of seconds to this duration and returns the new duration.
    
    
    

    * @param seconds The number of seconds to add.
    * @return A new duration which is the sum of the original duration and the specified number of seconds.
    */
    @CS2JWarning("Unhandled attribute removed: Pure")
    public final Duration addSeconds(double seconds) {
        return add(fromSeconds(seconds));
    }

    /**
    *  
    Adds a specified number of days to this duration and returns the new duration.
    
    
    

    * @param days The number of days to add.
    * @return A new duration which is the sum of the original duration and the specified number of days.
    */
    @CS2JWarning("Unhandled attribute removed: Pure")
    public final Duration addDays(double days) {
        return add(fromDays(days));
    }

    /**
    *  
    Returns true if two Durations are exactly equal, within the limits
    of floating point precision.  To be considered equal, the {@code Days} ({@link #getDays get})
    property must be identical and the
    difference between the {@code Seconds} ({@link #getSeconds get}) properties must be less than 1.0e-10.
    
    
    
    

    * @param left The left duration.
    * @param right The second duration.
    * @return {@code true} if the durations are equal, otherwise {@code false}.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'System.Boolean ==(Duration,Duration)'")
    public static boolean equals(Duration left, Duration right) {
        return left.equalsType(right);
    }

    /**
    *  
    Returns true if two Durations are NOT exactly equal, within the limits
    of floating point precision.  To be considered equal, the {@code Days} ({@link #getDays get})
    property must be identical (or one must be null) and the
    difference between the {@code Seconds} ({@link #getSeconds get}) properties must be less than 1.0e-10.
    
    
    
    

    * @param left The first duration.
    * @param right The second duration.
    * @return {@code true} if the durations are not equal, otherwise {@code false}.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'System.Boolean !=(Duration,Duration)'")
    public static boolean notEquals(Duration left, Duration right) {
        return !left.equalsType(right);
    }

    /**
    *  
    Returns true if {@code left} is shorter than {@code right}.
    
    
    
    

    * @param left The left duration.
    * @param right The right duration.
    * @return true if left is less than right, otherwise false.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'System.Boolean <(Duration,Duration)'")
    public static boolean lessThan(Duration left, Duration right) {
        return left.compareTo(right) < 0;
    }

    /**
    *  
    Returns true if {@code left} is longer than {@code right}.
    
    
    
    

    * @param left The left duration.
    * @param right The right duration.
    * @return true if left is greater than right, otherwise false.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'System.Boolean >(Duration,Duration)'")
    public static boolean greaterThan(Duration left, Duration right) {
        return left.compareTo(right) > 0;
    }

    /**
    *  
    Returns true if {@code left} is shorter than or exactly equal to {@code right}.
    
    
    
    

    * @param left The left duration.
    * @param right The right duration.
    * @return true if left is less than or equal to right, otherwise false.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'System.Boolean <=(Duration,Duration)'")
    public static boolean lessThanOrEqual(Duration left, Duration right) {
        return left.compareTo(right) <= 0;
    }

    /**
    *  
    Returns true if {@code left} is longer than or exactly equal to {@code right}.
    
    
    
    

    * @param left The left duration.
    * @param right The right duration.
    * @return true if left is greater than or equal to right, otherwise false.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'System.Boolean >=(Duration,Duration)'")
    public static boolean greaterThanOrEqual(Duration left, Duration right) {
        return left.compareTo(right) >= 0;
    }

    /**
    *  
    Adds two specified Duration instances.
    
    
    
    

    * @param left The first duration to add.
    * @param right The second duration to add.
    * @return The sum of {@code left} and {@code right}.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'Duration +(Duration,Duration)'")
    public static Duration add(Duration left, Duration right) {
        return left.add(right);
    }

    /**
    *  
    Subtracts a specified Duration from another specified Duration.
    
    
    
    

    * @param left The subtrahend.
    * @param right The minuend.
    * @return The difference {@code left} minus {@code right}.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'Duration -(Duration,Duration)'")
    public static Duration subtract(Duration left, Duration right) {
        return left.subtract(right);
    }

    /**
    *  
    Inverts a specified Duration.  For example, if the {@code value} represents a positive
    quantity of time, the returned Duration will be a negative quantity of time.
    
    
    

    * @param value The value to invert.
    * @return The inverted duration.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'Duration -(Duration)'")
    public static Duration negate(Duration value) {
        return new Duration(-value.m_days, -value.m_seconds);
    }

    /**
    *  
    Multiplies the duration by a constant.
    
    
    
    

    * @param left The duration to multiply.
    * @param right The constant by which to multiply the Duration.
    * @return A Duration that represents the value of this instance multiplied by the constant.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'Duration *(Duration,System.Double)'")
    public static Duration multiply(Duration left, double right) {
        return left.multiply(right);
    }

    /**
    *  
    Divides the duration by another duration.
    
    
    
    

    * @param dividend The duration to divide.
    * @param divisor The duration by which to divide the Duration.
    * @return The result of dividing the dividend by the divisor.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'System.Double /(Duration,Duration)'")
    public static double divide(Duration dividend, Duration divisor) {
        return dividend.divide(divisor);
    }

    /**
    *  
    Divides the duration by a constant.
    
    
    
    

    * @param dividend The duration to divide.
    * @param divisor The constant by which to divide the duration.
    * @return The result of dividing the dividend by the divisor.
    */
    @CS2JInfo("This method implements the functionality of the overloaded operator: 'Duration /(Duration,System.Double)'")
    public static Duration divide(Duration dividend, double divisor) {
        return dividend.divide(divisor);
    }

    /**
    *  
    Returns a Duration that represents the specified number of days.
    
    
    

    * @param days The number of days to be represented by the returned duration.
    * @return A {@link Duration} representing the specified number of days.
    */
    public static Duration fromDays(double days) {
        int wholeDays = (int) days;
        double seconds = (days - wholeDays) * TimeConstants.SecondsPerDay;
        return new Duration(wholeDays, seconds);
    }

    /**
    *  
    Returns a Duration that represents the specified number of seconds.
    
    
    

    * @param seconds The number of seconds to be represented by the returned duration.
    * @return A {@link Duration} representing the specified number of seconds.
    */
    public static Duration fromSeconds(double seconds) {
        return new Duration(0, seconds);
    }

    private int m_days;
    private double m_seconds;
    private static Duration s_maxValue = new Duration(Integer.MAX_VALUE, 0.0);
    private static Duration s_minValue = new Duration(Integer.MIN_VALUE, 0.0);
    private static Duration s_zero = new Duration(0, 0D);
}