package at.jku.softengws20.group1.detection.Map;

import at.jku.softengws20.group1.shared.Config;

public class InformationSign {
    private String text;

    public InformationSign(final String text) {
        this.text = text;
    }

    public InformationSign() {
        this.text = Config.STANDARD_TEST_INFO_SIGN;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void resetText() {
        text = Config.STANDARD_TEST_INFO_SIGN;
    }


}
