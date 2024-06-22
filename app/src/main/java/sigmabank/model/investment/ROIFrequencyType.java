package sigmabank.model.investment;

public enum ROIFrequencyType {
    DAILY("Diário"),
    WEEKLY("Semanal"),
    MONTHLY("Mensal"),
    YEARLY("Anual");

    private final String label;

    private ROIFrequencyType(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}