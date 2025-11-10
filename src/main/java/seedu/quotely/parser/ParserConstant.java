package seedu.quotely.parser;

public class ParserConstant {
    //Fixed Variable Declarations
    public static final double MAX_PRICE = 9999.99;
    public static final int MAX_QTY = 999;
    public static final int MAX_ITEMS = 30;
    public static final double MAX_TAX_RATE = 200.00;
    public static final int MAX_ITEMNAME_LENGTH = 30;
    public static final int MAX_QUOTENAME_LENGTH = 50;
    public static final int MAX_COMPANYNAME_LENGTH = 46;
    public static final int MAX_CUSTOMERNAME_LENGTH = 45;

    public static final String ADD_QUOTE_COMMAND_PATTERN = "^n/(.+?)\\s+c/(.+)$";
    public static final String QUOTENAME_ARG_PATTERN = "^n/(.+)$";
    public static final String EXPORT_QUOTENAME_ARG_PATTERN = "^n/(.+?)(?=\\s+f/|$)";
    public static final String EXPORT_START_PATTERN = "^(n/|f/)";
    public static final String FILENAME_ARG_PATTERN = "f/(.+)$";
    public static final String REGISTER_COMMAND_PATTERN = "^c/(.+)$";
    public static final String ADD_ITEM_COMMAND_PATTERN = 
        "^i/(.+?)(?:\\s+n/(.+?))?\\s+p/(.+?)\\s+q/(.+?)(?:\\s+t/(.+))?$";
    public static final String DELETE_ITEM_COMMAND_PATTERN = "^i/(.+?)(?:\s+n/(.+))?$";
}
