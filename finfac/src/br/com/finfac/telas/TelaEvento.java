/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.finfac.telas;

import java.sql.*;
import br.com.finfac.dao.ModuloConexao;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;


/**
 *
 * @author Usuário
 */
public class TelaEvento extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form TelaEvento
     */
    public TelaEvento() {
        initComponents();
        conexao = ModuloConexao.conectar();
    }
    
      private void adicionar(){
        String sql = "insert into tbeventos (descricao,dataevento,horario,localevento,categoria) values(?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEventDescricao.getText());
            pst.setString(2, txtEventId.getText());
           pst.setString(3, txtEventHorario.getText());
           pst.setString(4, txtEventLocal.getText());
pst.setString(5, cboEventCategoria.getSelectedItem().toString());

           if((txtEventDescricao.getText().isEmpty()) || (txtEventHorario.getText().isEmpty())){
               JOptionPane.showMessageDialog(null,"Preencha todos os campos");
           } else{
            
            int adicionado = pst.executeUpdate();
            System.out.println(adicionado);
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null,"Evento adicionado com sucesso");
                 limpar();
            }
           }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
        }
    }
      
   private void pesquisar_evento(){
    String sql = "SELECT * FROM tbeventos WHERE descricao LIKE ?";
    try {
        pst = conexao.prepareStatement(sql);
        pst.setString(1, "%" + txtEventPesquisar.getText() + "%");
        rs = pst.executeQuery();
        tblEventos.setModel(DbUtils.resultSetToTableModel(rs));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
   
   public void setar_campos(){
       int setar = tblEventos.getSelectedRow();
              txtEventId.setText(tblEventos.getModel().getValueAt(setar,0).toString());
       txtEventDescricao.setText(tblEventos.getModel().getValueAt(setar,1).toString());
       txtEventId.setText(tblEventos.getModel().getValueAt(setar,2).toString());
       txtEventHorario.setText(tblEventos.getModel().getValueAt(setar,3).toString());
       txtEventLocal.setText(tblEventos.getModel().getValueAt(setar,4).toString());
       // Obtém o valor para o JComboBox cboEventValidado
String valorValidado = tblEventos.getModel().getValueAt(setar, 5).toString();
cboEventValidado.setSelectedItem(valorValidado);

// Obtém o valor para o JComboBox cboEventCategoria
String valorCategoria = tblEventos.getModel().getValueAt(setar, 6).toString();
cboEventCategoria.setSelectedItem(valorCategoria);
btnAdicionar.setEnabled(false);
   }
   
   private void alterar(){
        String sql = "update tbeventos set descricao=?,dataevento=?,horario=?,localevento=?,validado=?,categoria=? where idevento=?";
        try {        
           pst = conexao.prepareStatement(sql);
           pst.setString(1, txtEventDescricao.getText());
           pst.setString(2, txtEventId.getText());
           pst.setString(3, txtEventHorario.getText());
           pst.setString(4, txtEventLocal.getText());
           pst.setString(5, cboEventValidado.getSelectedItem().toString());
           pst.setString(6, cboEventCategoria.getSelectedItem().toString());
           pst.setString(7, txtEventId.getText());
        
           if((txtEventDescricao.getText().isEmpty()) || (txtEventHorario.getText().isEmpty())){
               JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatorios");
           } else{
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null,"Usuario alterado com sucesso");
                 limpar();
            }
           }
        } catch (Exception e) {
         JOptionPane.showMessageDialog(null,e);
        }
    }

    private void remover(){
        int confirma = JOptionPane.showConfirmDialog(null, "tem certeza que deseja remover este evento ?", "Atenção",JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            String sql = "delete from tbeventos where idevento=?";
            try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEventId.getText());
           int apagado = pst.executeUpdate();
           if(apagado > 0){
               JOptionPane.showMessageDialog(null, "Evento Removido com sucesso");
               limpar();
               btnAdicionar.setEnabled(true);
           }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
    }
    
    private void limpar(){
        txtEventPesquisar.setText(null);
        txtEventDescricao.setText(null);
                  txtEventId.setText(null);
                txtEventHorario.setText(null);
                txtEventLocal.setText(null);
                ((DefaultTableModel) tblEventos.getModel()).setRowCount(0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtEventDescricao = new javax.swing.JTextArea();
        txtEventId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEventHorario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEventLocal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboEventValidado = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboEventCategoria = new javax.swing.JComboBox<>();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        txtEventPesquisar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEventos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtEventData1 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Eventos");
        setPreferredSize(new java.awt.Dimension(567, 514));

        jLabel1.setText("Descrição:");

        jLabel2.setText("Data:");

        txtEventDescricao.setColumns(20);
        txtEventDescricao.setRows(5);
        jScrollPane1.setViewportView(txtEventDescricao);

        txtEventId.setEnabled(false);
        txtEventId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEventIdActionPerformed(evt);
            }
        });

        jLabel3.setText("Horario:");

        jLabel4.setText("Local:");

        jLabel5.setText("Validado");

        cboEventValidado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sim", "Não" }));
        cboEventValidado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEventValidadoActionPerformed(evt);
            }
        });

        jLabel6.setText("Categoria");

        cboEventCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnRemover.setText("Remover");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        txtEventPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEventPesquisarKeyReleased(evt);
            }
        });

        jLabel7.setText("Pesquisar");

        tblEventos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Descrição", "Data", "Horario", "Local", "Validade", "Categoria"
            }
        ));
        tblEventos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEventosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblEventos);

        jLabel8.setText("Id Evento");

        txtEventData1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEventData1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(btnAdicionar)
                                .addGap(50, 50, 50)
                                .addComponent(btnAlterar)
                                .addGap(84, 84, 84)
                                .addComponent(btnRemover))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(txtEventPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel8))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtEventHorario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                            .addComponent(txtEventLocal, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(141, 141, 141)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(18, 18, 18)
                                                .addComponent(cboEventCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cboEventValidado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtEventId, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 543, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(83, 83, 83)
                    .addComponent(txtEventData1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(857, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEventPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtEventId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtEventHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cboEventValidado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEventLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cboEventCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAlterar)
                    .addComponent(btnRemover)
                    .addComponent(btnAdicionar))
                .addGap(37, 37, 37))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(287, Short.MAX_VALUE)
                    .addComponent(txtEventData1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(215, 215, 215)))
        );

        setBounds(0, 0, 1074, 560);
    }// </editor-fold>//GEN-END:initComponents

    private void cboEventValidadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEventValidadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEventValidadoActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
adicionar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtEventPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEventPesquisarKeyReleased
        pesquisar_evento();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEventPesquisarKeyReleased

    private void tblEventosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEventosMouseClicked
        setar_campos();
        // TODO add your handling code here:
    }//GEN-LAST:event_tblEventosMouseClicked

    private void txtEventIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEventIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEventIdActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtEventData1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEventData1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEventData1ActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboEventCategoria;
    private javax.swing.JComboBox<String> cboEventValidado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblEventos;
    private javax.swing.JTextField txtEventData1;
    private javax.swing.JTextArea txtEventDescricao;
    private javax.swing.JTextField txtEventHorario;
    private javax.swing.JTextField txtEventId;
    private javax.swing.JTextField txtEventLocal;
    private javax.swing.JTextField txtEventPesquisar;
    // End of variables declaration//GEN-END:variables
}
