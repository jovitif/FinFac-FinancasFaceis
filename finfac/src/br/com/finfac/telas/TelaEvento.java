/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.finfac.telas;

import java.sql.*;
import br.com.finfac.dao.ModuloConexao;
import java.awt.Color;
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
        getContentPane().setBackground(Color.WHITE);
        initComponents();
        conexao = ModuloConexao.conectar();
        pesquisar_evento();
    }
    
      private void adicionar(){
        String sql = "insert into tbeventos (descricao,dataevento,horario,localevento,validado,categoria) values(?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEventDescricao.getText());
            pst.setString(2, txtEventData.getText());
           pst.setString(3, txtEventHorario.getText());
           pst.setString(4, txtEventLocal.getText());
           pst.setString(5, cboEventValidado.getSelectedItem().toString());
           pst.setString(6, cboEventCategoria.getSelectedItem().toString());

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
       txtEventData.setText(tblEventos.getModel().getValueAt(setar,2).toString());
       txtEventHorario.setText(tblEventos.getModel().getValueAt(setar,3).toString());
       txtEventLocal.setText(tblEventos.getModel().getValueAt(setar,4).toString());
        cboEventValidado.setSelectedItem(tblEventos.getModel().getValueAt(setar, 5));
        cboEventCategoria.setSelectedItem(tblEventos.getValueAt(setar, 6));
        
btnAdicionar.setEnabled(false);
btnAlterar.setEnabled(true);
btnRemover.setEnabled(true);
   }
   
   private void alterar(){
       int confirma = JOptionPane.showConfirmDialog(null, "Confirma as alterações nos dados deste evento ?","Atenção!",JOptionPane.YES_NO_OPTION);
       if(confirma == JOptionPane.YES_OPTION){
                   String sql = "update tbeventos set descricao=?,dataevento=?,horario=?,localevento=?,validado=?,categoria=? where descricao=?";
        try {        
           pst = conexao.prepareStatement(sql);
           pst.setString(1, txtEventDescricao.getText());
           pst.setString(2, txtEventData.getText());
           pst.setString(3, txtEventHorario.getText());
           pst.setString(4, txtEventLocal.getText());
           pst.setString(5, cboEventValidado.getSelectedItem().toString());
           pst.setString(6, cboEventCategoria.getSelectedItem().toString());
           pst.setString(7, txtEventDescricao.getText());
        
           if((txtEventDescricao.getText().isEmpty()) || (txtEventHorario.getText().isEmpty())){
               JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatorios");
           } else{
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null,"Evento alterado com sucesso");
                 limpar();
            }
           }
        } catch (Exception e) {
         JOptionPane.showMessageDialog(null,e);
        }   
       }
        
    }

    private void remover(){
        int confirma = JOptionPane.showConfirmDialog(null, "tem certeza que deseja remover este evento ?", "Atenção",JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            String sql = "delete from tbeventos where descricao=?";
            try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEventDescricao.getText());
           int apagado = pst.executeUpdate();
           if(apagado > 0){
               limpar();
               JOptionPane.showMessageDialog(null, "Evento Removido com sucesso");
               
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
                txtEventData.setText(null);
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
        txtEventData = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Eventos");
        setPreferredSize(new java.awt.Dimension(567, 514));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Descrição:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, -1, -1));

        jLabel2.setText("Data:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 180, -1, -1));

        txtEventDescricao.setColumns(20);
        txtEventDescricao.setRows(5);
        jScrollPane1.setViewportView(txtEventDescricao);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 446, 44));

        txtEventId.setEnabled(false);
        txtEventId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEventIdActionPerformed(evt);
            }
        });
        getContentPane().add(txtEventId, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 122, 20));

        jLabel3.setText("Horario:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 240, -1, -1));
        getContentPane().add(txtEventHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 240, 127, -1));

        jLabel4.setText("Local:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, -1, -1));
        getContentPane().add(txtEventLocal, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 127, -1));

        jLabel5.setText("Validado");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 180, -1, -1));

        cboEventValidado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Obsevação", "Aprovado", "Recusado" }));
        cboEventValidado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEventValidadoActionPerformed(evt);
            }
        });
        getContentPane().add(cboEventValidado, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 180, -1, -1));

        jLabel6.setText("Categoria");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 240, -1, -1));

        cboEventCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Outros", "Alimentação", "Transporte", "Moradia", "Educação", "Lazer", "Compras" }));
        getContentPane().add(cboEventCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 240, -1, -1));

        btnAdicionar.setText("Adicionar");
        btnAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, -1, -1));

        btnAlterar.setText("Alterar");
        btnAlterar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAlterar, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 420, -1, -1));

        btnRemover.setText("Remover");
        btnRemover.setPreferredSize(new java.awt.Dimension(80, 80));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });
        getContentPane().add(btnRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 420, -1, -1));

        txtEventPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEventPesquisarActionPerformed(evt);
            }
        });
        txtEventPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEventPesquisarKeyReleased(evt);
            }
        });
        getContentPane().add(txtEventPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 280, -1));

        jLabel7.setText("Pesquisar");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 520, 440));

        jLabel8.setText("Id Evento");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, -1, -1));

        txtEventData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEventDataActionPerformed(evt);
            }
        });
        getContentPane().add(txtEventData, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 122, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("*Campo Obrigatorio");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 20, -1, -1));

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

    private void txtEventDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEventDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEventDataActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void txtEventPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEventPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEventPesquisarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboEventCategoria;
    public static javax.swing.JComboBox<String> cboEventValidado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JTextField txtEventData;
    private javax.swing.JTextArea txtEventDescricao;
    private javax.swing.JTextField txtEventHorario;
    private javax.swing.JTextField txtEventId;
    private javax.swing.JTextField txtEventLocal;
    private javax.swing.JTextField txtEventPesquisar;
    // End of variables declaration//GEN-END:variables
}
