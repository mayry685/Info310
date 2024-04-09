
import dao.ProductCollectionsDAO;
import dao.ProductDAO;
import gui.MainMenu;

/**
 * @author Mark George
 */
public class ProductCatalogue {

	public static void main(String[] args) {
		ProductDAO dao = new ProductCollectionsDAO();

		// create the frame instance
		MainMenu menu = new MainMenu(dao);

		// centre the frame on the screen
		menu.setLocationRelativeTo(null);

		// show the frame
		menu.setVisible(true);
	}

}
