/*
 */
package gui;

import dao.ProductDAO;
import domain.Product;
import helpers.SimpleListModel;
import java.util.Collection;
import javax.swing.JOptionPane;

/**
 * @author Mark George
 */
public class ProductViewer extends javax.swing.JDialog {

	private final ProductDAO dao;

	private final SimpleListModel productsModel = new SimpleListModel();

	public ProductViewer(java.awt.Frame parent, boolean modal, ProductDAO dao) {
		super(parent, modal);

		this.dao = dao;

		initComponents();

		// load products into JList
		Collection<Product> products = dao.getProducts();
		productsModel.updateItems(products);
		lstProducts.setModel(productsModel);

		// load categories into combo
		SimpleListModel categoriesModel = new SimpleListModel();
		Collection<String> categories = dao.getCategories();
		categoriesModel.updateItems(categories);
		cmbCategories.setModel(categoriesModel);
	}

	@SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      btnClose = new javax.swing.JButton();
      jScrollPane1 = new javax.swing.JScrollPane();
      lstProducts = new javax.swing.JList<>();
      btnDelete = new javax.swing.JButton();
      lblId = new javax.swing.JLabel();
      txtSearchId = new javax.swing.JTextField();
      btnSearch = new javax.swing.JButton();
      lblCategory = new javax.swing.JLabel();
      cmbCategories = new javax.swing.JComboBox<>();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setName("dlgViewer"); // NOI18N

      btnClose.setText("Close");
      btnClose.setName("btnClose"); // NOI18N
      btnClose.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnCloseActionPerformed(evt);
         }
      });

      jScrollPane1.setName("jScrollPane1"); // NOI18N

      lstProducts.setName("lstProducts"); // NOI18N
      jScrollPane1.setViewportView(lstProducts);

      btnDelete.setText("Delete");
      btnDelete.setName("btnDelete"); // NOI18N
      btnDelete.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnDeleteActionPerformed(evt);
         }
      });

      lblId.setText("ID:");
      lblId.setName("lblId"); // NOI18N

      txtSearchId.setName("txtSearchId"); // NOI18N

      btnSearch.setText("Search");
      btnSearch.setName("btnSearch"); // NOI18N
      btnSearch.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSearchActionPerformed(evt);
         }
      });

      lblCategory.setText("Category:");
      lblCategory.setName("lblCategory"); // NOI18N

      cmbCategories.setName("cmbCategories"); // NOI18N
      cmbCategories.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            cmbCategoriesActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addGap(157, 157, 157)
                  .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                     .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(lblId))
                     .addComponent(lblCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSearchId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch))
                     .addComponent(cmbCategories, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
               .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
            .addGap(12, 12, 12))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(lblId)
               .addComponent(txtSearchId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(btnSearch))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(lblCategory)
               .addComponent(cmbCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(btnClose)
               .addComponent(btnDelete))
            .addContainerGap())
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
		this.dispose();
   }//GEN-LAST:event_btnCloseActionPerformed

   private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
		if (!lstProducts.isSelectionEmpty()) {
			Product selected = lstProducts.getSelectedValue();

			int result = JOptionPane.showConfirmDialog(this, "Delete product " + selected.getProductName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

			// did the user click the yes button?
			if (result == JOptionPane.YES_OPTION) {
				dao.removeProduct(selected);

				// update JList
				productsModel.updateItems(dao.getProducts());

				// selected item was deleted, so clear selection
				lstProducts.clearSelection();
			}
		}
   }//GEN-LAST:event_btnDeleteActionPerformed

   private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
		String id = txtSearchId.getText();
		Product product = dao.searchById(id);
		productsModel.updateItems(product);
   }//GEN-LAST:event_btnSearchActionPerformed

   private void cmbCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoriesActionPerformed
		String category = (String) cmbCategories.getSelectedItem();
		Collection<Product> products = dao.filterByCategory(category);
		productsModel.updateItems(products);
   }//GEN-LAST:event_cmbCategoriesActionPerformed

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btnClose;
   private javax.swing.JButton btnDelete;
   private javax.swing.JButton btnSearch;
   private javax.swing.JComboBox<String> cmbCategories;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JLabel lblCategory;
   private javax.swing.JLabel lblId;
   private javax.swing.JList<Product> lstProducts;
   private javax.swing.JTextField txtSearchId;
   // End of variables declaration//GEN-END:variables
}
