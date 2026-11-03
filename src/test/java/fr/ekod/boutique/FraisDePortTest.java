package fr.ekod.boutique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FraisDePortTest {

    private static final Montant PETITE_COMMANDE = Montant.euros(20.00);

    private final FraisDePort fraisDePort = new FraisDePort();

    @Test
    void un_colis_de_100_g_en_france_coute_3_90_euros() {
        Montant frais = fraisDePort.calculer(500, Zone.FRANCE, PETITE_COMMANDE);

        assertEquals(Montant.euros(3.90), frais);
    }

    @Test
    void un_colis_de_800_g_en_france_coute_5_90_euros() {
        Montant frais = fraisDePort.calculer(800, Zone.FRANCE, PETITE_COMMANDE);

        assertEquals(Montant.euros(5.90), frais);
    }

    @Test
    void un_colis_de_3_kg_en_france_coute_8_90_euros() {
        Montant frais = fraisDePort.calculer(3_000, Zone.FRANCE, PETITE_COMMANDE);

        assertEquals(Montant.euros(8.90), frais);
    }

    @Test
    void un_colis_de_20_kg_en_france_coute_14_90_euros() {
        Montant frais = fraisDePort.calculer(20_000, Zone.FRANCE, PETITE_COMMANDE);

        assertEquals(Montant.euros(14.90), frais);
    }

    @Test
    void un_colis_de_800_g_en_europe_coute_12_50_euros() {
        Montant frais = fraisDePort.calculer(800, Zone.EUROPE, PETITE_COMMANDE);

        assertEquals(Montant.euros(12.50), frais);
    }

    @Test
    void un_colis_de_3_kg_dans_le_monde_coute_39_euros() {
        Montant frais = fraisDePort.calculer(3_000, Zone.MONDE, PETITE_COMMANDE);

        assertEquals(Montant.euros(39.00), frais);
    }

    @Test
    void un_colis_de_250_g_pile_reste_dans_la_tranche_basse() {
        Montant frais = fraisDePort.calculer(250, Zone.FRANCE, PETITE_COMMANDE);

        assertEquals(Montant.euros(3.90), frais);
    }

    @Test
    void un_colis_de_251_g_passe_dans_la_tranche_suivante() {
        Montant frais = fraisDePort.calculer(251, Zone.FRANCE, PETITE_COMMANDE);

        assertEquals(Montant.euros(5.90), frais);
    }

    @Test
    void le_port_est_offert_en_france_a_partir_de_60_euros_pile() {
        Montant frais = fraisDePort.calculer(800, Zone.FRANCE, Montant.euros(60.00));

        assertEquals(Montant.ZERO, frais);
    }

    @Test
    void le_port_n_est_pas_offert_en_france_a_59_99_euros() {
        Montant frais = fraisDePort.calculer(800, Zone.FRANCE, Montant.euros(59.99));

        assertEquals(Montant.euros(5.90), frais);
    }

    @Test
    void le_port_n_est_pas_offert_en_europe_meme_au_dessus_de_60_euros() {
        Montant frais = fraisDePort.calculer(800, Zone.EUROPE, Montant.euros(150.00));

        assertEquals(Montant.euros(12.50), frais);
    }

    @Test
    void un_colis_de_plus_de_30_kg_est_refuse() {
        assertThrows(IllegalArgumentException.class,
                () -> fraisDePort.calculer(30_001, Zone.FRANCE, PETITE_COMMANDE));
    }
}
