package org.davenportschools.westhigh.database;

import java.util.Locale;

public class StaffMember {
    public String firstName, lastName, email;

    @Override
    public String toString() {
        return String.format(Locale.US, "%s %s", firstName, lastName);
    }
}
