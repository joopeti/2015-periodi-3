package Utils;

/**
 *
 * @author joopeti
 */
public enum Hand {

    Kivi, Sakset, Paperi;
    private String ascii;
    private String ascii_reverse;

    static {
        Kivi.ascii
                = "          _______\n"
                + "      ---'   ____)\n"
                + "            (_____)\n"
                + "            (_____)\n"
                + "            (____)\n"
                + "      ---.__(___)";
        Sakset.ascii
                = "          _______\n"
                + "      ---'   ____)____\n"
                + "                ______)\n"
                + "             __________)\n"
                + "            (____)\n"
                + "      ---.__(___)";
        Paperi.ascii
                = "          _______\n"
                + "      ---'   ____)____\n"
                + "                ______)\n"
                + "                _______)\n"
                + "               _______)\n"
                + "      ---.__________)";
        Kivi.ascii_reverse
                = "       _______\n"
                + "     (____    '---\n"
                + "    (_____)\n"
                + "    (_____)\n"
                + "     (____)\n"
                + "      (___)__.---";
        Sakset.ascii_reverse
                = "          _______\n"
                + "     ____(____   '---\n"
                + "    (______\n"
                + "   (__________\n"
                + "         (____)\n"
                + "          (___)__.---";
        Paperi.ascii_reverse
                = "          _______\n"
                + "     ____(____   '---\n"
                + "    (______\n"
                + "   (_______\n"
                + "    (_______\n"
                + "      (__________.---";
    }

    public String toString(int p) {
        if (p == 1) {
            return ascii;
        } else {
            return ascii_reverse;
        }
    }

}
