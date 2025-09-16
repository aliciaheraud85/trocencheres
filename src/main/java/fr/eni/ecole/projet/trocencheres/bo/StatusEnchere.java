package fr.eni.ecole.projet.trocencheres.bo;

public class StatusEnchere {
    public static final int CREEE = 0;
    public static final int EN_COURS = 1;
    public static final int TERMINEE = 2;
    public static final int ANNULEE = 3;

    private final static String[] STATUTS = {
        "Créée",
        "En cours",
        "Terminée",
        "Annulée"
    };

    public static String getStatut(int code) {
        if (code < 0 || code >= STATUTS.length) {
            throw new IllegalArgumentException("Code de statut invalide: " + code);
        }
        return STATUTS[code];
    }

    private StatusEnchere() {
        // Private constructor to prevent instantiation
    }
}
