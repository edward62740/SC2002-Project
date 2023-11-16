package enums;

/**
 * The {@link RequestStatus} enumeration represents the various states a request
 * can be in.
 * <ul>
 * <li>{@link #PENDING}: The request is pending and awaiting a response.</li>
 * <li>{@link #REPLIED}: The request has been replied to.</li>
 * <li>{@link #ACCEPTED}: The request has been accepted.</li>
 * <li>{@link #REJECTED}: The request has been rejected.</li>
 * </ul>
 */
public enum RequestStatus {
    /**
     * The request is pending and awaiting a response.
     */
    PENDING,

    /**
     * The request has been replied to.
     */
    REPLIED,

    /**
     * The request has been accepted.
     */
    ACCEPTED,

    /**
     * The request has been rejected.
     */
    REJECTED,
}
