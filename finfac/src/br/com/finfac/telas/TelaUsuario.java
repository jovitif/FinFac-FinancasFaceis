/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.finfac.telas;

import java.awt.Color;

/**
 *
 * @author Usuário
 */

import java.sql.*;
import br.com.finfac.dao.ModuloConexao;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
         getContentPane().setBackground(Color.WHITE);
        initComponents();
        conexao = ModuloConexao.conectar();
    }
    
    private void consultar(){
        String sql = "select * from tbusuarios where nome = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtUsuPesquisar.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsuNome.setText(rs.getString(2));
                txtUsuEmail.setText(rs.getString(3));
                txtUsuDataNascimento.setText(rs.getString(4));
                cboUsuParentesco.setSelectedItem(rs.getString(5));
                txtUsuCpf.setText(rs.getString(6));
                 txtUsuFone.setText(rs.getString(7));
                 txtUsuLogin.setText(rs.getString(8));
                  txtUsuSenha.setText(rs.getString(9)); 
                  cboUsuPerfil.setSelectedItem(rs.getString(10));
            } else {
                JOptionPane.showMessageDialog(null, "Familiar não cadastrado");
                //limpar campos
                txtUsuNome.setText(null);
                txtUsuEmail.setText(null);
                txtUsuDataNascimento.setText(null);
                cboUsuParentesco.setSelectedItem(null);
                txtUsuCpf.setText(null);
                txtUsuFone.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null); 
                cboUsuPerfil.setSelectedItem(null);
                 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void adicionar(){
        String sql = "insert into tbusuarios(nome,email,datanascimento,parentesco,cpf,fone,login,senha,perfil) values(?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuEmail.getText());
            String dataNascimentoStr = txtUsuDataNascimento.getText();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dataNascimentoUtil = format.parse(dataNascimentoStr);
            java.sql.Date dataNascimentoSql = new java.sql.Date(dataNascimentoUtil.getTime());
            pst.setDate(3, dataNascimentoSql);
            pst.setString(4, cboUsuParentesco.getSelectedItem().toString());
            pst.setString(5, txtUsuCpf.getText());
            pst.setString(6, txtUsuFone.getText());
            pst.setString(7, txtUsuLogin.getText());
            pst.setString(8, txtUsuSenha.getText());
            pst.setString(9, cboUsuPerfil.getSelectedItem().toString());
           if((txtUsuNome.getText().isEmpty()) || (txtUsuEmail.getText().isEmpty()) || (txtUsuEmail.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())){
               JOptionPane.showMessageDialog(null,"Preencha todos os campos");
           } else{
            
            int adicionado = pst.executeUpdate();
            System.out.println(adicionado);
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null,"Familiar adicionado com sucesso");
                txtUsuNome.setText(null);
                txtUsuEmail.setText(null);
                txtUsuDataNascimento.setText(null);
                txtUsuCpf.setText(null);
                txtUsuFone.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null); 
            }
           }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void alterar(){
        String sql = "update tbusuarios set nome=?,email=?,datanascimento=?,parentesco=?,cpf=?,fone=?,login=?,senha=?,perfil=? where nome=?";
        try {        
           pst = conexao.prepareStatement(sql);
           pst.setString(1, txtUsuNome.getText());
           pst.setString(2, txtUsuEmail.getText());
           pst.setString(3, txtUsuDataNascimento.getText());
           pst.setString(4, cboUsuParentesco.getSelectedItem().toString());
           pst.setString(5, txtUsuCpf.getText());
           pst.setString(6, txtUsuFone.getText());
           pst.setString(7, txtUsuLogin.getText());
           pst.setString(8, txtUsuSenha.getText());
           pst.setString(9, cboUsuPerfil.getSelectedItem().toString());
           pst.setString(10, txtUsuPesquisar.getText());
           if((txtUsuNome.getText().isEmpty()) || (txtUsuEmail.getText().isEmpty()) || (txtUsuEmail.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())){
               JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatorios");
           } else{
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null,"Familiar alterado com sucesso");
                txtUsuNome.setText(null);
                txtUsuEmail.setText(null);
                txtUsuDataNascimento.setText(null);
                txtUsuCpf.setText(null);
                txtUsuFone.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null); 
            }
           }
        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void remover(){
        int confirma = JOptionPane.showConfirmDialog(null, "tem certeza que deseja remover este usuario", "Atenção",JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            String sql = "delete from tbusuarios where nome=?";
            try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
           int apagado = pst.executeUpdate();
           if(apagado > 0){
               JOptionPane.showMessageDialog(null, "Familiar Removido com sucesso");
                txtUsuNome.setText(null);
                txtUsuEmail.setText(null);
                txtUsuDataNascimento.setText(null);
                txtUsuCpf.setText(null);
                txtUsuFone.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null); 
           }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtUsuPesquisar = new javax.swing.JTextField();
        txtUsuNome = new javax.swing.JTextField();
        txtUsuEmail = new javax.swing.JTextField();
        txtUsuCpf = new javax.swing.JTextField();
        txtUsuFone = new javax.swing.JTextField();
        txtUsuLogin = new javax.swing.JTextField();
        txtUsuSenha = new javax.swing.JPasswordField();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        cboUsuParentesco = new javax.swing.JComboBox<>();
        txtUsuDataNascimento = new javax.swing.JTextField();
        btnUsuCreate = new javax.swing.JButton();
        btnUsuUpdate = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        btnUsuRead = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Familiares");
        setPreferredSize(new java.awt.Dimension(973, 514));

        jLabel1.setText("Pesquisar");

        jLabel2.setText("*Nome");

        jLabel3.setText("*Email");

        jLabel4.setText("parentesco");

        jLabel5.setText("Data Nascimento");

        jLabel6.setText("cpf");

        jLabel7.setText("Telefone");

        jLabel8.setText("*Login");

        jLabel9.setText("*Senha");

        jLabel11.setText("*Perfil");

        txtUsuPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuPesquisarActionPerformed(evt);
            }
        });

        txtUsuNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuNomeActionPerformed(evt);
            }
        });

        txtUsuEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuEmailActionPerformed(evt);
            }
        });

        txtUsuCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuCpfActionPerformed(evt);
            }
        });

        txtUsuFone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuFoneActionPerformed(evt);
            }
        });

        txtUsuLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuLoginActionPerformed(evt);
            }
        });

        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user", " " }));

        cboUsuParentesco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "sem parentesco", "pais", "avós", "bisavós", "filhos", "netos", "bisnetos", "sogro", "sogra", "genro", "nora", "padrasto", "madrasta", "enteado", "irmãos", "tios", "sobrinhos", "primos", "filho adotivo", "pai/mãe adotivo(a)", "cônjuge", "companheiro(a)" }));

        txtUsuDataNascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuDataNascimentoActionPerformed(evt);
            }
        });

        btnUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/plus.png"))); // NOI18N
        btnUsuCreate.setToolTipText("Adicionar");
        btnUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreateActionPerformed(evt);
            }
        });

        btnUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/edit.png"))); // NOI18N
        btnUsuUpdate.setToolTipText("Atualizar");
        btnUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });

        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/cross.png"))); // NOI18N
        btnUsuDelete.setToolTipText("Deletar");
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });

        btnUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/search.png"))); // NOI18N
        btnUsuRead.setToolTipText("Consultar");
        btnUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("*Campo Obrigatorio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnUsuRead)
                        .addGap(231, 231, 231))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtUsuCpf))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboUsuParentesco, 0, 177, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtUsuDataNascimento))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2))
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUsuNome, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                    .addComponent(txtUsuEmail)
                                    .addComponent(txtUsuPesquisar))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 310, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(10, 10, 10)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(227, 227, 227)
                                        .addComponent(jLabel12))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtUsuPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtUsuEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtUsuDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(cboUsuParentesco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtUsuCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUsuUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUsuDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUsuCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32))
        );

        setBounds(0, 0, 1074, 560);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuPesquisarActionPerformed

    private void txtUsuNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuNomeActionPerformed

    private void txtUsuEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuEmailActionPerformed

    private void txtUsuCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuCpfActionPerformed

    private void txtUsuFoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuFoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuFoneActionPerformed

    private void txtUsuLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuLoginActionPerformed

    private void txtUsuDataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuDataNascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuDataNascimentoActionPerformed

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
        consultar();
// TODO add your handling code here:
    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void btnUsuCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuCreateActionPerformed
        // chamando o metodo adicionar
            adicionar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsuCreateActionPerformed

    private void btnUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUpdateActionPerformed
        alterar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsuUpdateActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        remover();
// TODO add your handling code here:
    }//GEN-LAST:event_btnUsuDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuCreate;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JComboBox<String> cboUsuParentesco;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtUsuCpf;
    private javax.swing.JTextField txtUsuDataNascimento;
    private javax.swing.JTextField txtUsuEmail;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuPesquisar;
    private javax.swing.JPasswordField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
