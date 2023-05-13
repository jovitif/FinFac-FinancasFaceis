/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.finfac.telas;

import java.awt.Color;

/**
 *
 * @author João Sales
 */
import java.sql.*;
import br.com.finfac.dao.ModuloConexao;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

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
        pesquisar_usuario();
    }

    private void consultar() {
        String sql = "select * from tbusuarios where nome = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuPesquisar1.getText());
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

    private void adicionar() {
        String sql = "insert into tbusuarios(nome,email,datanascimento,parentesco,cpf,fone,login,senha,perfil) values(?,?,?,?,?,?,?,?,?)";
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
            if ((txtUsuNome.getText().isEmpty()) || (txtUsuEmail.getText().isEmpty()) || (txtUsuEmail.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {
                int adicionado = pst.executeUpdate();
                System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Familiar adicionado com sucesso");
                    limpar();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void limpar() {
        txtUsuNome.setText(null);
        txtUsuEmail.setText(null);
        txtUsuDataNascimento.setText(null);
        txtUsuCpf.setText(null);
        txtUsuFone.setText(null);
        txtUsuLogin.setText(null);
        txtUsuSenha.setText(null);
        cboUsuParentesco.setSelectedItem(0);
        cboUsuPerfil.setSelectedItem(0);
        btnUsuCreate.setEnabled(true);
        btnUsuDelete.setEnabled(false);
        btnUsuUpdate.setEnabled(false);
    }

       private void pesquisar_usuario() {
        String sql = "SELECT *  FROM tbusuarios WHERE nome LIKE ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "%" + txtUsuPesquisar1.getText() + "%");
            rs = pst.executeQuery();
            tblUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setarCampos() {
        int setar = tblUsuarios.getSelectedRow();
        txtUsuNome.setText(tblUsuarios.getValueAt(setar, 1).toString());
        txtUsuEmail.setText(tblUsuarios.getValueAt(setar, 2).toString());
        txtUsuDataNascimento.setText(tblUsuarios.getValueAt(setar, 3).toString());
        cboUsuParentesco.setSelectedItem(tblUsuarios.getValueAt(setar, 4));
        txtUsuCpf.setText(tblUsuarios.getValueAt(setar, 5).toString());
        txtUsuFone.setText(tblUsuarios.getValueAt(setar, 6).toString());
        txtUsuLogin.setText(tblUsuarios.getValueAt(setar, 7).toString());
        txtUsuSenha.setText(tblUsuarios.getValueAt(setar, 8).toString());
        cboUsuPerfil.setSelectedItem(tblUsuarios.getValueAt(setar, 9));

        btnUsuCreate.setEnabled(false);
        btnUsuUpdate.setEnabled(true);
        btnUsuDelete.setEnabled(true);
    }


    private void alterar() {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirmar as alterações nos dados deste cleinte ?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
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
                pst.setString(10, txtUsuNome.getText());
                if ((txtUsuNome.getText().isEmpty()) || (txtUsuEmail.getText().isEmpty()) || (txtUsuEmail.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios");
                } else {
                    int adicionado = pst.executeUpdate();
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Familiar alterado com sucesso");
                        limpar();
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }


    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "tem certeza que deseja remover este familiar", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbusuarios where nome=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuNome.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    limpar();
                    JOptionPane.showMessageDialog(null, "Familiar Removido com sucesso");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
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
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtUsuPesquisar1 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Familiares");
        setPreferredSize(new java.awt.Dimension(973, 514));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("*Nome");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(491, 64, -1, -1));

        jLabel3.setText("*Email");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, -1, 22));

        jLabel4.setText("Parentesco");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, -1, -1));

        jLabel5.setText("Data Nascimento");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, -1, -1));

        jLabel6.setText("CPF");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, -1, -1));

        jLabel7.setText("Telefone");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 60, -1, -1));

        jLabel8.setText("*Login");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, -1, -1));

        jLabel9.setText("*Senha");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 170, -1, -1));

        jLabel11.setText("*Perfil");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 280, -1, -1));

        txtUsuNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuNomeActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsuNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 61, 230, -1));

        txtUsuEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuEmailActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsuEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, 224, -1));

        txtUsuCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuCpfActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsuCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(584, 280, 200, -1));

        txtUsuFone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuFoneActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsuFone, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 60, 170, -1));

        txtUsuLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuLoginActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsuLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 110, 170, -1));
        getContentPane().add(txtUsuSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 170, 170, -1));

        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user", " " }));
        getContentPane().add(cboUsuPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 280, 170, -1));

        cboUsuParentesco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "sem parentesco", "pais", "avós", "bisavós", "filhos", "netos", "bisnetos", "sogro", "sogra", "genro", "nora", "padrasto", "madrasta", "enteado", "irmãos", "tios", "sobrinhos", "primos", "filho adotivo", "pai/mãe adotivo(a)", "cônjuge", "companheiro(a)" }));
        getContentPane().add(cboUsuParentesco, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 220, 190, -1));

        txtUsuDataNascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuDataNascimentoActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsuDataNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 170, 190, -1));

        btnUsuCreate.setBackground(new java.awt.Color(78, 92, 188));
        btnUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/add.png"))); // NOI18N
        btnUsuCreate.setToolTipText("Adicionar");
        btnUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreateActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 410, -1, -1));

        btnUsuUpdate.setBackground(new java.awt.Color(78, 92, 188));
        btnUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/editar.png"))); // NOI18N
        btnUsuUpdate.setToolTipText("Atualizar");
        btnUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 410, -1, -1));

        btnUsuDelete.setBackground(new java.awt.Color(78, 92, 188));
        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/trash.png"))); // NOI18N
        btnUsuDelete.setToolTipText("Deletar");
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 410, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("*Campo Obrigatorio");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 18, -1, -1));

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nome", "Email", "Nascimento", "Parentesco", "Perfil"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 58, -1, -1));

        jLabel10.setText("Pesquisar");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 21, -1, 20));

        txtUsuPesquisar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuPesquisar1ActionPerformed(evt);
            }
        });
        txtUsuPesquisar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsuPesquisar1KeyReleased(evt);
            }
        });
        getContentPane().add(txtUsuPesquisar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 18, 187, -1));

        setBounds(0, 0, 1074, 560);
    }// </editor-fold>//GEN-END:initComponents

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

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        setarCampos();
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void txtUsuPesquisar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuPesquisar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuPesquisar1ActionPerformed

    private void txtUsuPesquisar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuPesquisar1KeyReleased
        // TODO add your handling code here:
        pesquisar_usuario();
    }//GEN-LAST:event_txtUsuPesquisar1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuCreate;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JComboBox<String> cboUsuParentesco;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtUsuCpf;
    private javax.swing.JTextField txtUsuDataNascimento;
    private javax.swing.JTextField txtUsuEmail;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuPesquisar1;
    private javax.swing.JPasswordField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
