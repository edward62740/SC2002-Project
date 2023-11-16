package views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.IntStream;

import enums.UserRole;
import models.Camp;
import models.User;

/**
 * The {@link CampView} class provides methods for printing camps and its attributes to CLI.
 */
public class CampView {
    private CampView() {}

    /**
     * DateTime formatter for printing date
     */
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Formats and prints camp information based on its reference and subject to the {@link UserRole}.
     * @param camp the camp to print
     * @param user which user is viewing the information
     */
    public static void printCamp(Camp camp, User user) {
        System.out.println("\033[1;34m--------------------------------------\033[0m");
        System.out.println("\033[1;36mCamp name:\033[0m " + camp.getName());
        System.out.println("\033[1;36mID:\033[0m " + camp.getCampId());
        System.out.println("\033[1;36mDescription:\033[0m " + camp.getDescription());
        System.out.println("\033[1;36mFaculty:\033[0m " + camp.getUserGroup());
        System.out.println("\033[1;36mLocation:\033[0m " + camp.getLocation());
        System.out.println("\033[1;36mStaff in-charge:\033[0m " + camp.getStaff());
        System.out.println("\033[1;36mRemaining Member Slots:\033[0m " + (camp.getTotalSlots() - camp.getRegisteredStudents().size() + " out of " + camp.getTotalSlots()));
        System.out.println("\033[1;36mRemaining Committee Slots:\033[0m " + (camp.getCcmSlots() - camp.getCommittee().size() + " out of " + camp.getCcmSlots()));
        if (camp.getClosingDate() != null) System.out.println("\033[1;36mRegistration closing date:\033[0m " + camp.getClosingDate().format(formatter));
        System.out.println("\033[1;36mCamp dates:\033[0m ");
        printDateArray(camp);

        if (user.getRole() == UserRole.STAFF || user.getRole() == UserRole.CCM) {
            System.out.println("\033[1;36mList of committee members:\033[0m ");
            for (String s : camp.getCommittee()) System.out.print(s + ", ");
            if(camp.getCommittee().size() == 0) System.out.print("None");
            System.out.println("");
            System.out.println("\033[1;36mList of normal members:\033[0m ");
            for (String s : camp.getRegisteredStudents()) System.out.print(s + ", ");
            if(camp.getRegisteredStudents().size() == 0) System.out.print("None");
            System.out.println("");
        }
        System.out.println("\033[1;34m--------------------------------------\033[0m");
    }

    /**
     * Formats and prints the position of a given user for a given camp.
     * @param camp the camp to check
     * @param user the user to check
     */
    public static void printPosition(Camp camp, User user) {
        if (camp.getCommittee().contains(user.getUserID())) System.out.println("\033[1;36mPosition:\033[0m Committee");
        else if (camp.getRegisteredStudents().contains(user.getUserID())) System.out.println("\033[1;36mPosition:\033[0m Member");
        else System.out.println("\033[1;36mPosition:\033[0m None");
    }

    /**
     * Formats and prints a date array from {@link Camp}.
     * @param camp the camp to print dates from
     */
    public static void printDateArray(Camp camp) {
        if (camp.getDates() != null) {
            IntStream.range(0, camp.getDates().size())
                    .forEach(index ->
                            System.out.println(index + ": " +
                                    camp.getDates().get(index).getKey().format(formatter) + " - " +
                                    camp.getDates().get(index).getValue().format(formatter)
                            )
                    );
        }
        if(camp.getDates().size() == 0) System.out.print("None");
    }
}
