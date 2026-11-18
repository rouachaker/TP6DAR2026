package corbaClient;

import corbaBanque.*;

import javax.naming.Context;
import javax.naming.InitialContext;

public class BanqueClient {

    public static void main(String[] args) {

        try {
            // 1. Se connecter a l'annuaire
            Context context = new InitialContext();

            // 2. Recuperer l'objet distant
            Object obj = context.lookup("Banque");

            // 3. Creer le Stub avec narrow
            IBanqueRemote banque =
                    IBanqueRemoteHelper.narrow(
                            (org.omg.CORBA.Object) obj
                    );

            // 4. Creer deux comptes
            banque.creerCompte(new Compte(1, 1000));
            banque.creerCompte(new Compte(2, 2000));

            System.out.println("Comptes crees");

            // 5. Verser 500 DT
            banque.verser(500, 1);

            // 6. Retirer 200 DT
            banque.retirer(200, 1);

            // 7. Consulter un compte
            Compte c = banque.getCompte(1);

            System.out.println("Compte : " + c.code);
            System.out.println("Solde : " + c.solde);

            // 8. Afficher tous les comptes
            Compte[] comptes = banque.getComptes();

            System.out.println("Liste des comptes :");

            for (Compte compte : comptes) {
                System.out.println(
                        "Code : " + compte.code +
                                " | Solde : " + compte.solde
                );
            }

            // 9. Conversion euro vers dinar
            double montant = banque.conversion(100);

            System.out.println(
                    "100 euros = " + montant + " DT"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
