package gui;

import dao.ProductDAO;
import domain.Product;
import helpers.SimpleListModel;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Collection;
import javax.swing.JOptionPane;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * @author Mark George
 */
public class ProductEditor extends javax.swing.JDialog {

	private final ProductDAO dao;

	public ProductEditor(java.awt.Frame parent, boolean modal, ProductDAO dao) {
		super(parent, modal);

		this.dao = dao;

		initComponents();

		cmbCategory.setEditable(true);

		// load categories into combo
		SimpleListModel categoriesModel = new SimpleListModel();
		Collection<String> categories = dao.getCategories();
		categoriesModel.updateItems(categories);
		cmbCategory.setModel(categoriesModel);
	}

	@SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel1 = new javax.swing.JLabel();
      jLabel2 = new javax.swing.JLabel();
      txtId = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();
      txtName = new javax.swing.JTextField();
      jLabel4 = new javax.swing.JLabel();
      jScrollPane1 = new javax.swing.JScrollPane();
      txtDescription = new javax.swing.JTextArea();
      jLabel6 = new javax.swing.JLabel();
      cmbCategory = new javax.swing.JComboBox<>();
      jLabel5 = new javax.swing.JLabel();
      jLabel7 = new javax.swing.JLabel();
      btnSave = new javax.swing.JButton();
      btnCancel = new javax.swing.JButton();
      txtPrice = new javax.swing.JFormattedTextField();
      txtQuantity = new javax.swing.JTextField();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setName("dlgEditor"); // NOI18N

      jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
      jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel1.setText("Product Editor");
      jLabel1.setName("jLabel1"); // NOI18N

      jLabel2.setText("ID:");
      jLabel2.setName("jLabel2"); // NOI18N

      txtId.setName("txtId"); // NOI18N

      jLabel3.setText("Name:");
      jLabel3.setName("jLabel3"); // NOI18N

      txtName.setName("txtName"); // NOI18N

      jLabel4.setText("Description:");
      jLabel4.setName("jLabel4"); // NOI18N

      jScrollPane1.setName("jScrollPane1"); // NOI18N

      txtDescription.setColumns(20);
      txtDescription.setRows(5);
      txtDescription.setName("txtDescription"); // NOI18N
      txtDescription.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyPressed(java.awt.event.KeyEvent evt) {
            txtDescriptionKeyPressed(evt);
         }
      });
      jScrollPane1.setViewportView(txtDescription);

      jLabel6.setText("Category:");
      jLabel6.setName("jLabel6"); // NOI18N

      cmbCategory.setName("cmbCategory"); // NOI18N

      jLabel5.setText("Price:");
      jLabel5.setName("jLabel5"); // NOI18N

      jLabel7.setText("Quantity:");
      jLabel7.setName("jLabel7"); // NOI18N

      btnSave.setText("Save");
      btnSave.setName("btnSave"); // NOI18N
      btnSave.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSaveActionPerformed(evt);
         }
      });

      btnCancel.setText("Cancel");
      btnCancel.setName("btnCancel"); // NOI18N
      btnCancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnCancelActionPerformed(evt);
         }
      });

      txtPrice.setName("txtPrice"); // NOI18N

      txtQuantity.setName("txtQuantity"); // NOI18N

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
            .addGap(254, 254, 254)
            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
            .addContainerGap())
         .addGroup(layout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(12, 12, 12))
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
               .addComponent(jLabel2)
               .addComponent(jLabel3)
               .addComponent(jLabel4)
               .addComponent(jLabel6)
               .addComponent(jLabel5)
               .addComponent(jLabel7))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(txtId)
               .addComponent(txtName)
               .addComponent(jScrollPane1)
               .addComponent(cmbCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(txtPrice)
               .addComponent(txtQuantity))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel2)
               .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel3)
               .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel4)
               .addComponent(jScrollPane1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel6)
               .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel5)
               .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel7)
               .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(btnSave)
               .addComponent(btnCancel))
            .addContainerGap())
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
		try {
			String id = txtId.getText();
			String name = txtName.getText();
			String description = txtDescription.getText();
			String category = (String) cmbCategory.getSelectedItem();
			BigDecimal price = new BigDecimal(txtPrice.getText());
			BigDecimal quantity = new BigDecimal(txtQuantity.getText());

			Product product = new Product();

			product.setProductId(id);
			product.setProductName(name);
			product.setDescription(description);
			product.setCategory(category);
			product.setListPrice(price);
			product.setQuantityInStock(quantity);

			new Validator().assertValid(product);

			dao.saveProduct(product);
			dispose();

		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "You have entered a price or quantity that is not a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
		} catch (ConstraintsViolatedException ex) {

			// get the violated constraints from the exception
			ConstraintViolation[] violations = ex.getConstraintViolations();

			// create a nice error message for the user
			String msg = "Please fix the following input problems:";
			for (ConstraintViolation cv : violations) {
				msg += "\n  - " + cv.getMessage();
			}

			// display the message to the user
			JOptionPane.showMessageDialog(this, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
		}
   }//GEN-LAST:event_btnSaveActionPerformed

   private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
		this.dispose();
   }//GEN-LAST:event_btnCancelActionPerformed

   private void txtDescriptionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescriptionKeyPressed
		// Make the tab key change focus when in the description text field.
		// The default behaviour is to enter a tab character which is annoying.
		if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			evt.consume();
			cmbCategory.requestFocus();
		}
   }//GEN-LAST:event_txtDescriptionKeyPressed

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btnCancel;
   private javax.swing.JButton btnSave;
   private javax.swing.JComboBox<String> cmbCategory;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JTextArea txtDescription;
   private javax.swing.JTextField txtId;
   private javax.swing.JTextField txtName;
   private javax.swing.JFormattedTextField txtPrice;
   private javax.swing.JTextField txtQuantity;
   // End of variables declaration//GEN-END:variables
}
