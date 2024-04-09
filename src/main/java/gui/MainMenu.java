package gui;

import dao.ProductDAO;

/**
 * @author Mark George
 */
public class MainMenu extends javax.swing.JFrame {

	private final ProductDAO dao;

	public MainMenu(ProductDAO dao) {
		this.dao = dao;
		initComponents();
	}

	@SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel1 = new javax.swing.JLabel();
      btnAddNewProduct = new javax.swing.JButton();
      btnViewProducts = new javax.swing.JButton();
      btnExit = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      jLabel1.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
      jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel1.setText("Product Catalogue");
      jLabel1.setName("jLabel1"); // NOI18N

      btnAddNewProduct.setText("Add a New Product");
      btnAddNewProduct.setName("btnAddNewProduct"); // NOI18N
      btnAddNewProduct.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnAddNewProductActionPerformed(evt);
         }
      });

      btnViewProducts.setText("View Products");
      btnViewProducts.setName("btnViewProducts"); // NOI18N
      btnViewProducts.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnViewProductsActionPerformed(evt);
         }
      });

      btnExit.setText("Exit");
      btnExit.setName("btnExit"); // NOI18N
      btnExit.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnExitActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(btnAddNewProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(btnViewProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1)
            .addGap(18, 18, 18)
            .addComponent(btnAddNewProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(18, 18, 18)
            .addComponent(btnViewProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(18, 18, 18)
            .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(18, 18, 18))
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
		System.exit(0);
   }//GEN-LAST:event_btnExitActionPerformed

   private void btnAddNewProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewProductActionPerformed
		ProductEditor editor = new ProductEditor(this, true, dao);
		editor.setLocationRelativeTo(null);
		editor.setVisible(true);
   }//GEN-LAST:event_btnAddNewProductActionPerformed

   private void btnViewProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewProductsActionPerformed
		ProductViewer viewer = new ProductViewer(this, true, dao);
		viewer.setLocationRelativeTo(null);
		viewer.setVisible(true);
   }//GEN-LAST:event_btnViewProductsActionPerformed

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btnAddNewProduct;
   private javax.swing.JButton btnExit;
   private javax.swing.JButton btnViewProducts;
   private javax.swing.JLabel jLabel1;
   // End of variables declaration//GEN-END:variables
}
