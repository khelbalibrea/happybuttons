/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package happybuttons;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Michael Balibrea
 */
public class SystemClass {
    public static void UITheme(String themeProfile) {
        if(themeProfile.equals("light")) { // ----------------------------------------------------------------------------- LIGHT THEME (DEFAULT)
            HappyButtons.mf.getContentPane().setBackground(new JPanel().getBackground());
            
            // -------------------------------------------------------------------------------------- MENU BAR
            MainFrame.jMenuBar1.setOpaque(false);
            MainFrame.jMenuBar1.setBackground(new JMenuBar().getBackground());
            
            // -------------------------------------------------------------------------------------- COMBO BOX
//            MainFrame.cboVidLoop.setBackground(new JComboBox().getBackground());
//            MainFrame.cboVidLoop.setForeground(new JComboBox().getForeground());
            
            // -------------------------------------------------------------------------------------- MENUS
            MainFrame.jMenu1.setForeground(new JMenu().getForeground());
            MainFrame.itmTools.setForeground(new JMenu().getForeground());
            MainFrame.itmAbout.setForeground(new JMenu().getForeground());
            MainFrame.menuPreferences.setForeground(new JMenu().getForeground());
            MainFrame.jMenuTime.setForeground(new JMenu().getForeground());
            
            // -------------------------------------------------------------------------------------- MENU ITEMS
            String itmNewIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\new_workspace_12px.png");
            MainFrame.itmNew.setBackground(new JMenuItem().getBackground());
            MainFrame.itmNew.setForeground(new JMenuItem().getForeground());
            MainFrame.itmNew.setIcon(new javax.swing.ImageIcon(itmNewIcon));
            
            String itmSaveIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\save_workspace_12px.png");
            MainFrame.itemSave.setBackground(new JMenuItem().getBackground());
            MainFrame.itemSave.setForeground(new JMenuItem().getForeground());
            MainFrame.itemSave.setIcon(new javax.swing.ImageIcon(itmSaveIcon));
            
            String itmLoadIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\download_12px.png");
            MainFrame.itmLoad.setBackground(new JMenuItem().getBackground());
            MainFrame.itmLoad.setForeground(new JMenuItem().getForeground());
            MainFrame.itmLoad.setIcon(new javax.swing.ImageIcon(itmLoadIcon));
            
            String itmResourceIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\maintenance_12px.png");
            MainFrame.itmResourceManager.setBackground(new JMenuItem().getBackground());
            MainFrame.itmResourceManager.setForeground(new JMenuItem().getForeground());
            MainFrame.itmResourceManager.setIcon(new javax.swing.ImageIcon(itmResourceIcon));
            
            String itmUIThemeIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\pagelines_12px.png");
            MainFrame.itmUITheme.setBackground(new JMenuItem().getBackground());
            MainFrame.itmUITheme.setForeground(new JMenuItem().getForeground());
            MainFrame.itmUITheme.setIcon(new javax.swing.ImageIcon(itmUIThemeIcon));
            
            String itmPluginsIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\plugins_12px.png");
            MainFrame.itmPlugins.setBackground(new JMenuItem().getBackground());
            MainFrame.itmPlugins.setForeground(new JMenuItem().getForeground());
            MainFrame.itmPlugins.setIcon(new javax.swing.ImageIcon(itmPluginsIcon));
            
            String itmSettingsIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\settings_12px.png");
            MainFrame.itmSettings.setBackground(new JMenuItem().getBackground());
            MainFrame.itmSettings.setForeground(new JMenuItem().getForeground());
            MainFrame.itmSettings.setIcon(new javax.swing.ImageIcon(itmSettingsIcon));
            
            String itmToolsIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\full_tool_storage_box_12px.png");
            MainFrame.itmSystemTools.setBackground(new JMenuItem().getBackground());
            MainFrame.itmSystemTools.setForeground(new JMenuItem().getForeground());
            MainFrame.itmSystemTools.setIcon(new javax.swing.ImageIcon(itmToolsIcon));
                        
            // -------------------------------------------------------------------------------------- PANELS
            MainFrame.panelRow1.setBackground(new JPanel().getBackground());
            MainFrame.panelRow2.setBackground(new JPanel().getBackground());
            MainFrame.panelRow3.setBackground(new JPanel().getBackground());
            MainFrame.panelJList.setBackground(new JPanel().getBackground());
            
            MainFrame.panelSFX1.setBackground(new JPanel().getBackground());
            MainFrame.panelSFX1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            MainFrame.panelSFX2.setBackground(new JPanel().getBackground());
            MainFrame.panelSFX2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            MainFrame.panelSFX3.setBackground(new JPanel().getBackground());
            MainFrame.panelSFX3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            MainFrame.panelR1S01.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S02.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S03.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S04.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S05.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S06.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S07.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S08.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S09.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S10.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S11.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S12.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S13.setBackground(new JPanel().getBackground());
            MainFrame.panelR1S14.setBackground(new JPanel().getBackground());
            
            MainFrame.panelR2S01.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S02.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S03.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S04.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S05.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S06.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S07.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S08.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S09.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S10.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S11.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S12.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S13.setBackground(new JPanel().getBackground());
            MainFrame.panelR2S14.setBackground(new JPanel().getBackground());
            
            MainFrame.panelR3S01.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S02.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S03.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S04.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S05.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S06.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S07.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S08.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S09.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S10.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S11.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S12.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S13.setBackground(new JPanel().getBackground());
            MainFrame.panelR3S14.setBackground(new JPanel().getBackground());
            
            // -------------------------------------------------------------------------------------- BUTTONS
            MainFrame.btnPlayPauseBGM1.setBackground(new JButton().getBackground());
            MainFrame.btnPlayPauseBGM2.setBackground(new JButton().getBackground());
            String btnBGMPlayPauseIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_12px.png");
            MainFrame.btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnBGMPlayPauseIcon));
            MainFrame.btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnBGMPlayPauseIcon));
            
            MainFrame.btnStopBGM1.setBackground(new JButton().getBackground());
            MainFrame.btnStopBGM2.setBackground(new JButton().getBackground());
            String btnStopSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\stop_12px.png");
            MainFrame.btnStopBGM1.setIcon(new javax.swing.ImageIcon(btnStopSFXIcon));
            MainFrame.btnStopBGM2.setIcon(new javax.swing.ImageIcon(btnStopSFXIcon));
            
            MainFrame.btnClearBGM1.setBackground(new JButton().getBackground());
            MainFrame.btnClearBGM2.setBackground(new JButton().getBackground());
            String btnClearSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\cancel_12px.png");
            MainFrame.btnClearBGM1.setIcon(new javax.swing.ImageIcon(btnClearSFXIcon));
            MainFrame.btnClearBGM2.setIcon(new javax.swing.ImageIcon(btnClearSFXIcon));
            
            MainFrame.btnAddBGM.setBackground(new JButton().getBackground());
            String btnAddBGMIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\add_bgm_12px.png");
            MainFrame.btnAddBGM.setIcon(new javax.swing.ImageIcon(btnAddBGMIcon));
            
            MainFrame.btnDeleteBGM.setBackground(new JButton().getBackground());
            String btnDeleteBGMIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\delete_bgm_14px.png");
            MainFrame.btnDeleteBGM.setIcon(new javax.swing.ImageIcon(btnDeleteBGMIcon));
            
            MainFrame.btnAddSFX.setBackground(new JButton().getBackground());
            String btnAddSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\add_sfx_12px.png");
            MainFrame.btnAddSFX.setIcon(new javax.swing.ImageIcon(btnAddSFXIcon));
            
            MainFrame.btnDeleteSFX.setBackground(new JButton().getBackground());
            String btnDeleteSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\delete_sfx_14px.png");
            MainFrame.btnDeleteSFX.setIcon(new javax.swing.ImageIcon(btnDeleteSFXIcon));
            
            MainFrame.btnStopSFX.setBackground(new JButton().getBackground());
            String btnStopAllSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\stop_sfx_12px.png");
            MainFrame.btnStopSFX.setIcon(new javax.swing.ImageIcon(btnStopAllSFXIcon));
            
            MainFrame.btnPlayVL.setBackground(new JButton().getBackground());
            String btnPlayVLIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_vl_12px.png");
            MainFrame.btnPlayVL.setIcon(new javax.swing.ImageIcon(btnPlayVLIcon));
            
            MainFrame.btnStopVL.setBackground(new JButton().getBackground());
            String btnStopVLIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\stop_vl_12px.png");
            MainFrame.btnStopVL.setIcon(new javax.swing.ImageIcon(btnStopVLIcon));
            
            MainFrame.btnPrevMp3.setBackground(new JButton().getBackground());
            String btnPrevMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\back_mp3_ui_12px.png");
            MainFrame.btnPrevMp3.setIcon(new javax.swing.ImageIcon(btnPrevMp3Icon));
            
            MainFrame.btnPlayPauseMp3.setBackground(new JButton().getBackground());
            String btnPlayPauseMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_12px.png");
            MainFrame.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnPlayPauseMp3Icon));
            
            MainFrame.btnNextMp3.setBackground(new JButton().getBackground());
            String btnNextMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\next_mp3_ui_12px.png");
            MainFrame.btnNextMp3.setIcon(new javax.swing.ImageIcon(btnNextMp3Icon));
                        
            String sfxButton = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\wave_black_14px.png");
            MainFrame.btnR1SFX01.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX01.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX02.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX02.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX03.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX03.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX04.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX04.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX05.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX05.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX06.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX06.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX07.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX07.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX08.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX08.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX09.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX09.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX10.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX10.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX11.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX11.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX12.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX12.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX13.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX13.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX14.setBackground(new JButton().getBackground());
            MainFrame.btnR1SFX14.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX01.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX01.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX02.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX02.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX03.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX03.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX04.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX04.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX05.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX05.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX06.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX06.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX07.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX07.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX08.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX08.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX09.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX09.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX10.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX10.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX11.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX11.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX12.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX12.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX13.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX13.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX14.setBackground(new JButton().getBackground());
            MainFrame.btnR2SFX14.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX01.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX01.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX02.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX02.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX03.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX03.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX04.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX04.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX05.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX05.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX06.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX06.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX07.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX07.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX08.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX08.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX09.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX09.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX10.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX10.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX11.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX11.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX12.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX12.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX13.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX13.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX14.setBackground(new JButton().getBackground());
            MainFrame.btnR3SFX14.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            // -------------------------------------------------------------------------------------- TOGGLE BUTTON
            MainFrame.togLinkBGMVol.setBackground(new JToggleButton().getBackground());
            MainFrame.togLinkBGMVol.setForeground(new JToggleButton().getForeground());
            
            // -------------------------------------------------------------------------------------- LIST
            MainFrame.listBGM.setBackground(new JList().getBackground());
            MainFrame.listBGM.setForeground(new JList().getForeground());
            MainFrame.listBGM.setBorder(BorderFactory.createTitledBorder(null, 
                    "BGM", 
                    TitledBorder.LEFT, 
                    TitledBorder.TOP, 
                    new Font("segoe", Font.BOLD,12), 
                    new JList().getForeground()));
            
            MainFrame.listSFX.setBackground(new JList().getBackground());
            MainFrame.listSFX.setForeground(new JList().getForeground());
            MainFrame.listSFX.setBorder(BorderFactory.createTitledBorder(null, 
                    "SFX", 
                    TitledBorder.LEFT, 
                    TitledBorder.TOP, 
                    new Font("segoe", Font.BOLD,12), 
                    new JList().getForeground()));
            
            // -------------------------------------------------------------------------------------- TEXT FIELDS
            MainFrame.tfBGM1.setBackground(new JTextField().getBackground());
            MainFrame.tfBGM1.setForeground(new JTextField().getForeground());
            
            MainFrame.tfBGM2.setBackground(new JTextField().getBackground());
            MainFrame.tfBGM2.setForeground(new JTextField().getForeground());
            
            MainFrame.tfLastOperation.setBackground(new JTextField().getBackground());
            MainFrame.tfLastOperation.setForeground(new JTextField().getForeground());
            
            MainFrame.tfMp3.setBackground(new JTextField().getBackground());
            MainFrame.tfMp3.setForeground(new JTextField().getForeground());
            
            MainFrame.tfVideoLoop.setBackground(new JTextField().getBackground());
            MainFrame.tfVideoLoop.setForeground(new JTextField().getForeground());
            
            // -------------------------------------------------------------------------------------- LABELS
            MainFrame.lblBGM1.setBackground(new JLabel().getBackground());
            MainFrame.lblBGM1.setForeground(new JLabel().getForeground());
            MainFrame.lblBGM1.setOpaque(true);
            
            MainFrame.lblBGM2.setBackground(new JLabel().getBackground());
            MainFrame.lblBGM2.setForeground(new JLabel().getForeground());
            MainFrame.lblBGM2.setOpaque(true);
            
            MainFrame.lblVolBGM1.setBackground(new JLabel().getBackground());
            MainFrame.lblVolBGM1.setForeground(new JLabel().getForeground());
            MainFrame.lblVolBGM1.setOpaque(true);
            
            MainFrame.lblVolBGM2.setBackground(new JLabel().getBackground());
            MainFrame.lblVolBGM2.setForeground(new JLabel().getForeground());
            MainFrame.lblVolBGM2.setOpaque(true);
            
            MainFrame.lblLastOperation.setBackground(new JLabel().getBackground());
            MainFrame.lblLastOperation.setForeground(new JLabel().getForeground());
            MainFrame.lblLastOperation.setOpaque(true);
            
            MainFrame.lblCurrentMp3Duration.setBackground(new JLabel().getBackground());
            MainFrame.lblCurrentMp3Duration.setForeground(new JLabel().getForeground());
            MainFrame.lblCurrentMp3Duration.setOpaque(true);
            
            MainFrame.lblLinkBGMVolumes.setBackground(new JLabel().getBackground());
            MainFrame.lblLinkBGMVolumes.setForeground(new JLabel().getForeground());
            MainFrame.lblLinkBGMVolumes.setOpaque(true);
                    
            MainFrame.lblVolSFX.setBackground(new JLabel().getBackground());
            MainFrame.lblVolSFX.setForeground(new JLabel().getForeground());
            MainFrame.lblVolSFX.setOpaque(true);
            
            MainFrame.lblSFXState.setBackground(new JLabel().getBackground());
            MainFrame.lblSFXState.setForeground(new JLabel().getForeground());
            MainFrame.lblSFXState.setOpaque(true);
            
            MainFrame.lblVideoLoop.setBackground(new JLabel().getBackground());
            MainFrame.lblVideoLoop.setForeground(new JLabel().getForeground());
            MainFrame.lblVideoLoop.setOpaque(true);
            
            MainFrame.lblMp3.setBackground(new JLabel().getBackground());
            MainFrame.lblMp3.setForeground(new JLabel().getForeground());
            MainFrame.lblMp3.setOpaque(true);
            
            MainFrame.lblR1SFX01.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX01.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX01.setOpaque(true);
            
            MainFrame.lblR1SFX02.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX02.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX02.setOpaque(true);
            
            MainFrame.lblR1SFX03.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX03.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX03.setOpaque(true);
            
            MainFrame.lblR1SFX04.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX04.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX04.setOpaque(true);
            
            MainFrame.lblR1SFX05.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX05.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX05.setOpaque(true);
            
            MainFrame.lblR1SFX06.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX06.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX06.setOpaque(true);
            
            MainFrame.lblR1SFX07.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX07.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX07.setOpaque(true);
            
            MainFrame.lblR1SFX08.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX08.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX08.setOpaque(true);
            
            MainFrame.lblR1SFX09.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX09.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX09.setOpaque(true);
            
            MainFrame.lblR1SFX10.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX10.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX10.setOpaque(true);
            
            MainFrame.lblR1SFX11.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX11.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX11.setOpaque(true);
            
            MainFrame.lblR1SFX12.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX12.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX12.setOpaque(true);
            
            MainFrame.lblR1SFX13.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX13.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX13.setOpaque(true);
            
            MainFrame.lblR1SFX14.setBackground(new JLabel().getBackground());
            MainFrame.lblR1SFX14.setForeground(new JLabel().getForeground());
            MainFrame.lblR1SFX14.setOpaque(true);
            
            MainFrame.lblR2SFX01.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX01.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX01.setOpaque(true);
            
            MainFrame.lblR2SFX02.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX02.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX02.setOpaque(true);
            
            MainFrame.lblR2SFX03.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX03.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX03.setOpaque(true);
            
            MainFrame.lblR2SFX04.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX04.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX04.setOpaque(true);
            
            MainFrame.lblR2SFX05.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX05.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX05.setOpaque(true);
            
            MainFrame.lblR2SFX06.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX06.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX06.setOpaque(true);
            
            MainFrame.lblR2SFX07.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX07.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX07.setOpaque(true);
            
            MainFrame.lblR2SFX08.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX08.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX08.setOpaque(true);
            
            MainFrame.lblR2SFX09.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX09.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX09.setOpaque(true);
            
            MainFrame.lblR2SFX10.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX10.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX10.setOpaque(true);
            
            MainFrame.lblR2SFX11.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX11.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX11.setOpaque(true);
            
            MainFrame.lblR2SFX12.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX12.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX12.setOpaque(true);
            
            MainFrame.lblR2SFX13.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX13.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX13.setOpaque(true);
            
            MainFrame.lblR2SFX14.setBackground(new JLabel().getBackground());
            MainFrame.lblR2SFX14.setForeground(new JLabel().getForeground());
            MainFrame.lblR2SFX14.setOpaque(true);
            
            MainFrame.lblR3SFX01.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX01.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX01.setOpaque(true);
            
            MainFrame.lblR3SFX02.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX02.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX02.setOpaque(true);
            
            MainFrame.lblR3SFX03.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX03.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX03.setOpaque(true);
            
            MainFrame.lblR3SFX04.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX04.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX04.setOpaque(true);
            
            MainFrame.lblR3SFX05.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX05.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX05.setOpaque(true);
            
            MainFrame.lblR3SFX06.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX06.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX06.setOpaque(true);
            
            MainFrame.lblR3SFX07.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX07.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX07.setOpaque(true);
            
            MainFrame.lblR3SFX08.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX08.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX08.setOpaque(true);
            
            MainFrame.lblR3SFX09.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX09.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX09.setOpaque(true);
            
            MainFrame.lblR3SFX10.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX10.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX10.setOpaque(true);
            
            MainFrame.lblR3SFX11.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX11.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX11.setOpaque(true);
            
            MainFrame.lblR3SFX12.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX12.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX12.setOpaque(true);
            
            MainFrame.lblR3SFX13.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX13.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX13.setOpaque(true);
            
            MainFrame.lblR3SFX14.setBackground(new JLabel().getBackground());
            MainFrame.lblR3SFX14.setForeground(new JLabel().getForeground());
            MainFrame.lblR3SFX14.setOpaque(true);
            
            // -------------------------------------------------------------------------------------- CHECK BOX
            MainFrame.chkLoop1.setBackground(new JCheckBox().getBackground());
            MainFrame.chkLoop1.setForeground(new JCheckBox().getForeground());
            
            MainFrame.chkLoop2.setBackground(new JCheckBox().getBackground());
            MainFrame.chkLoop2.setForeground(new JCheckBox().getForeground());
            
            MainFrame.chkLoopVL.setBackground(new JCheckBox().getBackground());
            MainFrame.chkLoopVL.setForeground(new JCheckBox().getForeground());
            
            MainFrame.chkMuteVL.setBackground(new JCheckBox().getBackground());
            MainFrame.chkMuteVL.setForeground(new JCheckBox().getForeground()); 
            
            MainFrame.chkFitVL.setBackground(new JCheckBox().getBackground());
            MainFrame.chkFitVL.setForeground(new JCheckBox().getForeground());
            
            MainFrame.chkVLMode.setBackground(new JCheckBox().getBackground());
            MainFrame.chkVLMode.setForeground(new JCheckBox().getForeground());
            
            // -------------------------------------------------------------------------------------- RADIO BUTTON
            MainFrame.chkSP.setBackground(new JRadioButton().getBackground());
            MainFrame.chkSP.setForeground(new JRadioButton().getForeground());
            
            MainFrame.chkIB.setBackground(new JRadioButton().getBackground());
            MainFrame.chkIB.setForeground(new JRadioButton().getForeground());
            
            // -------------------------------------------------------------------------------------- VOLUME BAR
            MainFrame.volBGM1.setBackground(new JSlider().getBackground());
            MainFrame.volBGM2.setBackground(new JSlider().getBackground());
            MainFrame.volSFX.setBackground(new JSlider().getBackground());
//            if(MainFrame.mp3FrameOpened == 1) {
//                // ------------------------------------------------------------------------------- PANELS
//
//                // ------------------------------------------------------------------------------- BUTTONS
//                Mp3Frame.btnAddMp3.setBackground(new JButton().getBackground());
//                String btnAddMp3Icon1 = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\add_mp3_12px.png");
//                Mp3Frame.btnAddMp3.setIcon(new javax.swing.ImageIcon(btnAddMp3Icon1));
//
//                Mp3Frame.btnDeleteMp3.setBackground(new JButton().getBackground());
//                String btnDeleteMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\delete_mp3_ui_12px.png");
//                Mp3Frame.btnDeleteMp3.setIcon(new javax.swing.ImageIcon(btnDeleteMp3Icon));
//
//                Mp3Frame.btnBackMp3.setBackground(new JButton().getBackground());
//                String btnBackMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\back_mp3_ui_16px.png");
//                Mp3Frame.btnBackMp3.setIcon(new javax.swing.ImageIcon(btnBackMp3Icon));
//
//                Mp3Frame.btnPlayPauseMp3.setBackground(new JButton().getBackground());
//                String btnPlayMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\play_mp3_ui_18px.png");
//                Mp3Frame.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnPlayMp3Icon));
//
//                Mp3Frame.btnNextMp3.setBackground(new JButton().getBackground());
//                String btnNextMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\next_mp3_ui_16px.png");
//                Mp3Frame.btnNextMp3.setIcon(new javax.swing.ImageIcon(btnNextMp3Icon));
//
//                // ------------------------------------------------------------------------------- LABELS
//                Mp3Frame.lblSongMp3.setBackground(new JLabel().getBackground());
//                Mp3Frame.lblSongMp3.setForeground(new JLabel().getForeground());
//                Mp3Frame.lblSongMp3.setOpaque(true);
//
//                Mp3Frame.lblLastFrame.setBackground(new JLabel().getBackground());
//                Mp3Frame.lblLastFrame.setForeground(new JLabel().getForeground());
//                Mp3Frame.lblLastFrame.setOpaque(true);
//
//                Mp3Frame.lblDuration.setBackground(new JLabel().getBackground());
//                Mp3Frame.lblDuration.setForeground(new JLabel().getForeground());
//                Mp3Frame.lblDuration.setOpaque(true);
//
//                // ------------------------------------------------------------------------------- SLIDERS
//                Mp3Frame.volMp3.setBackground(new JSlider().getBackground());
//                Mp3Frame.sliderSongTime.setBackground(new JSlider().getBackground());
//
//                // ------------------------------------------------------------------------------- TEXT FIELDS
//                Mp3Frame.tfSearch.setBackground(new JTextField().getBackground());
//                Mp3Frame.tfSearch.setForeground(new JTextField().getForeground());
//
//                Mp3Frame.listMp3.setBackground(new JList().getBackground());
//                Mp3Frame.listMp3.setForeground(new JList().getForeground());
//                MainFrame.listBGM.setBorder(BorderFactory.createTitledBorder(null, 
//                        "", 
//                        TitledBorder.LEFT, 
//                        TitledBorder.TOP, 
//                        new Font("segoe", Font.BOLD,12), 
//                        new JList().getForeground()));
//            }
        }
        else if(themeProfile.equals("dark")) { // ------------------------------------------------------------------------------- DARK THEME
            HappyButtons.mf.getContentPane().setBackground(Color.DARK_GRAY);
            
            // -------------------------------------------------------------------------------------- LIST
            MainFrame.listBGM.setBackground(Color.DARK_GRAY);
            MainFrame.listBGM.setForeground(Color.LIGHT_GRAY);
            MainFrame.listBGM.setBorder(BorderFactory.createTitledBorder(null, 
                    "BGM", 
                    TitledBorder.LEFT, 
                    TitledBorder.TOP, 
                    new Font("segoe", Font.BOLD,12), 
                    Color.LIGHT_GRAY));
            
            MainFrame.listSFX.setBackground(Color.DARK_GRAY);
            MainFrame.listSFX.setForeground(Color.LIGHT_GRAY);
            MainFrame.listSFX.setBorder(BorderFactory.createTitledBorder(null, 
                    "SFX", 
                    TitledBorder.LEFT, 
                    TitledBorder.TOP, 
                    new Font("segoe", Font.BOLD,12), 
                    Color.LIGHT_GRAY));
            
            // -------------------------------------------------------------------------------------- MENU BAR
            MainFrame.jMenuBar1.setOpaque(true);
            MainFrame.jMenuBar1.setBackground(Color.BLACK);
            MainFrame.jMenuBar1.setForeground(Color.WHITE);
            
            // -------------------------------------------------------------------------------------- COMBO BOX
//            MainFrame.cboVidLoop.setBackground(Color.DARK_GRAY);
//            MainFrame.cboVidLoop.setForeground(Color.lightGray);
            
            // -------------------------------------------------------------------------------------- MENUS
            MainFrame.jMenu1.setForeground(Color.lightGray);
            MainFrame.itmTools.setForeground(Color.lightGray);
            MainFrame.itmAbout.setForeground(Color.lightGray);
            MainFrame.menuPreferences.setForeground(Color.lightGray);
            MainFrame.jMenuTime.setForeground(Color.gray);
            
            // -------------------------------------------------------------------------------------- MENU ITEMS
            String itmNewIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_new_copy_12px.png");
            MainFrame.itmNew.setBackground(Color.darkGray);
            MainFrame.itmNew.setForeground(Color.lightGray);
            MainFrame.itmNew.setIcon(new javax.swing.ImageIcon(itmNewIcon));
            
            String itmSaveIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_save_12px.png");
            MainFrame.itemSave.setBackground(Color.darkGray);
            MainFrame.itemSave.setForeground(Color.lightGray);
            MainFrame.itemSave.setIcon(new javax.swing.ImageIcon(itmSaveIcon));
            
            String itmLoadIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_download_12px.png");
            MainFrame.itmLoad.setBackground(Color.darkGray);
            MainFrame.itmLoad.setForeground(Color.lightGray);
            MainFrame.itmLoad.setIcon(new javax.swing.ImageIcon(itmLoadIcon));
            
            String itmResourceIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_maintenance_12px.png");
            MainFrame.itmResourceManager.setBackground(Color.darkGray);
            MainFrame.itmResourceManager.setForeground(Color.lightGray);
            MainFrame.itmResourceManager.setIcon(new javax.swing.ImageIcon(itmResourceIcon));
            
            String itmUIThemeIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_pagelines_12px.png");
            MainFrame.itmUITheme.setBackground(Color.darkGray);
            MainFrame.itmUITheme.setForeground(Color.lightGray);
            MainFrame.itmUITheme.setIcon(new javax.swing.ImageIcon(itmUIThemeIcon));
            
            String itmPluginsIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_plugins_12px.png");
            MainFrame.itmPlugins.setBackground(Color.darkGray);
            MainFrame.itmPlugins.setForeground(Color.lightGray);
            MainFrame.itmPlugins.setIcon(new javax.swing.ImageIcon(itmPluginsIcon));
            
            String itmSettingsIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_settings_12px.png");
            MainFrame.itmSettings.setBackground(Color.darkGray);
            MainFrame.itmSettings.setForeground(Color.lightGray);
            MainFrame.itmSettings.setIcon(new javax.swing.ImageIcon(itmSettingsIcon));
            
            String itmToolsIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_full_tool_storage_box_12px.png");
            MainFrame.itmSystemTools.setBackground(Color.darkGray);
            MainFrame.itmSystemTools.setForeground(Color.lightGray);
            MainFrame.itmSystemTools.setIcon(new javax.swing.ImageIcon(itmToolsIcon));
            
            // -------------------------------------------------------------------------------------- PANELS
            MainFrame.panelRow1.setBackground(Color.DARK_GRAY);
            MainFrame.panelRow2.setBackground(Color.DARK_GRAY);
            MainFrame.panelRow3.setBackground(Color.DARK_GRAY);
            MainFrame.panelJList.setBackground(Color.DARK_GRAY);
            
            MainFrame.panelSFX1.setBackground(Color.DARK_GRAY);
            MainFrame.panelSFX1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            MainFrame.panelSFX2.setBackground(Color.DARK_GRAY);
            MainFrame.panelSFX2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            MainFrame.panelSFX3.setBackground(Color.DARK_GRAY);
            MainFrame.panelSFX3.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            MainFrame.panelR1S01.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S02.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S03.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S04.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S05.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S06.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S07.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S08.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S09.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S10.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S11.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S12.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S13.setBackground(Color.DARK_GRAY);
            MainFrame.panelR1S14.setBackground(Color.DARK_GRAY);
            
            MainFrame.panelR2S01.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S02.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S03.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S04.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S05.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S06.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S07.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S08.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S09.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S10.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S11.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S12.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S13.setBackground(Color.DARK_GRAY);
            MainFrame.panelR2S14.setBackground(Color.DARK_GRAY);
            
            MainFrame.panelR3S01.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S02.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S03.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S04.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S05.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S06.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S07.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S08.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S09.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S10.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S11.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S12.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S13.setBackground(Color.DARK_GRAY);
            MainFrame.panelR3S14.setBackground(Color.DARK_GRAY);
            
            // -------------------------------------------------------------------------------------- BUTTONS
            MainFrame.btnPlayPauseBGM1.setBackground(Color.GRAY);
            MainFrame.btnPlayPauseBGM2.setBackground(Color.GRAY);
            String btnBGMPlayPauseIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_12px.png");
            MainFrame.btnPlayPauseBGM1.setIcon(new javax.swing.ImageIcon(btnBGMPlayPauseIcon));
            MainFrame.btnPlayPauseBGM2.setIcon(new javax.swing.ImageIcon(btnBGMPlayPauseIcon));
            
            MainFrame.btnStopBGM1.setBackground(Color.GRAY);
            MainFrame.btnStopBGM2.setBackground(Color.GRAY);
            String btnStopSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_stop_12px.png");
            MainFrame.btnStopBGM1.setIcon(new javax.swing.ImageIcon(btnStopSFXIcon));
            MainFrame.btnStopBGM2.setIcon(new javax.swing.ImageIcon(btnStopSFXIcon));
            
            MainFrame.btnClearBGM1.setBackground(Color.GRAY);
            MainFrame.btnClearBGM2.setBackground(Color.GRAY);
            String btnClearSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_cancel_12px.png");
            MainFrame.btnClearBGM1.setIcon(new javax.swing.ImageIcon(btnClearSFXIcon));
            MainFrame.btnClearBGM2.setIcon(new javax.swing.ImageIcon(btnClearSFXIcon));
            
            MainFrame.btnAddBGM.setBackground(Color.GRAY);
            String btnAddBGMIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_add_bgm_12px.png");
            MainFrame.btnAddBGM.setIcon(new javax.swing.ImageIcon(btnAddBGMIcon));
            
            MainFrame.btnDeleteBGM.setBackground(Color.GRAY);
            String btnDeleteBGMIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_delete_bgm_12px.png");
            MainFrame.btnDeleteBGM.setIcon(new javax.swing.ImageIcon(btnDeleteBGMIcon));
            
            MainFrame.btnAddSFX.setBackground(Color.GRAY);
            String btnAddSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_add_sfx_12px.png");
            MainFrame.btnAddSFX.setIcon(new javax.swing.ImageIcon(btnAddSFXIcon));
            
            MainFrame.btnDeleteSFX.setBackground(Color.GRAY);
            String btnDeleteSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_delete_sfx_12px.png");
            MainFrame.btnDeleteSFX.setIcon(new javax.swing.ImageIcon(btnDeleteSFXIcon));
            
            MainFrame.btnStopSFX.setBackground(Color.GRAY);
            String btnStopAllSFXIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_sfx_stop_12px.png");
            MainFrame.btnStopSFX.setIcon(new javax.swing.ImageIcon(btnStopAllSFXIcon));
            
            MainFrame.btnPlayVL.setBackground(Color.GRAY);
            String btnPlayVLIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_vl_12px.png");
            MainFrame.btnPlayVL.setIcon(new javax.swing.ImageIcon(btnPlayVLIcon));
            
            MainFrame.btnStopVL.setBackground(Color.GRAY);
            String btnStopVLIcon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_stop_vl_12px.png");
            MainFrame.btnStopVL.setIcon(new javax.swing.ImageIcon(btnStopVLIcon));
            
            MainFrame.btnPrevMp3.setBackground(Color.GRAY);
            String btnPrevMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_back_mp3_ui_12px.png");
            MainFrame.btnPrevMp3.setIcon(new javax.swing.ImageIcon(btnPrevMp3Icon));
            
            MainFrame.btnPlayPauseMp3.setBackground(Color.GRAY);
            String btnPlayPauseMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_play_mp3_12px.png");
            MainFrame.btnPlayPauseMp3.setIcon(new javax.swing.ImageIcon(btnPlayPauseMp3Icon));
            
            MainFrame.btnNextMp3.setBackground(Color.GRAY);
            String btnNextMp3Icon = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_next_mp3_ui_12px.png");
            MainFrame.btnNextMp3.setIcon(new javax.swing.ImageIcon(btnNextMp3Icon));
            
            String sfxButton = HappyButtons.documentsPathDoubleSlash + Utility.strDoubleSlash("\\HappyButtons\\res\\icon\\dark_theme\\dark_Audio Wave_48px.png");
            MainFrame.btnR1SFX01.setBackground(Color.GRAY);
            MainFrame.btnR1SFX01.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX02.setBackground(Color.GRAY);
            MainFrame.btnR1SFX02.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX03.setBackground(Color.GRAY);
            MainFrame.btnR1SFX03.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX04.setBackground(Color.GRAY);
            MainFrame.btnR1SFX04.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX05.setBackground(Color.GRAY);
            MainFrame.btnR1SFX05.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX06.setBackground(Color.GRAY);
            MainFrame.btnR1SFX06.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX07.setBackground(Color.GRAY);
            MainFrame.btnR1SFX07.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX08.setBackground(Color.GRAY);
            MainFrame.btnR1SFX08.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX09.setBackground(Color.GRAY);
            MainFrame.btnR1SFX09.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX10.setBackground(Color.GRAY);
            MainFrame.btnR1SFX10.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX11.setBackground(Color.GRAY);
            MainFrame.btnR1SFX11.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX12.setBackground(Color.GRAY);
            MainFrame.btnR1SFX12.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX13.setBackground(Color.GRAY);
            MainFrame.btnR1SFX13.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR1SFX14.setBackground(Color.GRAY);
            MainFrame.btnR1SFX14.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX01.setBackground(Color.GRAY);
            MainFrame.btnR2SFX01.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX02.setBackground(Color.GRAY);
            MainFrame.btnR2SFX02.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX03.setBackground(Color.GRAY);
            MainFrame.btnR2SFX03.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX04.setBackground(Color.GRAY);
            MainFrame.btnR2SFX04.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX05.setBackground(Color.GRAY);
            MainFrame.btnR2SFX05.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX06.setBackground(Color.GRAY);
            MainFrame.btnR2SFX06.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX07.setBackground(Color.GRAY);
            MainFrame.btnR2SFX07.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX08.setBackground(Color.GRAY);
            MainFrame.btnR2SFX08.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX09.setBackground(Color.GRAY);
            MainFrame.btnR2SFX09.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX10.setBackground(Color.GRAY);
            MainFrame.btnR2SFX10.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX11.setBackground(Color.GRAY);
            MainFrame.btnR2SFX11.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX12.setBackground(Color.GRAY);
            MainFrame.btnR2SFX12.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX13.setBackground(Color.GRAY);
            MainFrame.btnR2SFX13.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR2SFX14.setBackground(Color.GRAY);
            MainFrame.btnR2SFX14.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX01.setBackground(Color.GRAY);
            MainFrame.btnR3SFX01.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX02.setBackground(Color.GRAY);
            MainFrame.btnR3SFX02.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX03.setBackground(Color.GRAY);
            MainFrame.btnR3SFX03.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX04.setBackground(Color.GRAY);
            MainFrame.btnR3SFX04.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX05.setBackground(Color.GRAY);
            MainFrame.btnR3SFX05.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX06.setBackground(Color.GRAY);
            MainFrame.btnR3SFX06.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX07.setBackground(Color.GRAY);
            MainFrame.btnR3SFX07.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX08.setBackground(Color.GRAY);
            MainFrame.btnR3SFX08.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX09.setBackground(Color.GRAY);
            MainFrame.btnR3SFX09.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX10.setBackground(Color.GRAY);
            MainFrame.btnR3SFX10.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX11.setBackground(Color.GRAY);
            MainFrame.btnR3SFX11.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX12.setBackground(Color.GRAY);
            MainFrame.btnR3SFX12.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX13.setBackground(Color.GRAY);
            MainFrame.btnR3SFX13.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            MainFrame.btnR3SFX14.setBackground(Color.GRAY);
            MainFrame.btnR3SFX14.setIcon(new javax.swing.ImageIcon(sfxButton));
            
            // -------------------------------------------------------------------------------------- TOGGLE BUTTON
            MainFrame.togLinkBGMVol.setBackground(Color.GRAY);
            MainFrame.togLinkBGMVol.setForeground(Color.WHITE);
            
            // -------------------------------------------------------------------------------------- TEXT FIELDS
            MainFrame.tfBGM1.setBackground(Color.DARK_GRAY);
            MainFrame.tfBGM1.setForeground(Color.LIGHT_GRAY);
            
            MainFrame.tfBGM2.setBackground(Color.DARK_GRAY);
            MainFrame.tfBGM2.setForeground(Color.LIGHT_GRAY);
            
            MainFrame.tfLastOperation.setBackground(Color.DARK_GRAY);
            MainFrame.tfLastOperation.setForeground(Color.LIGHT_GRAY);
            
            MainFrame.tfMp3.setBackground(Color.DARK_GRAY);
            MainFrame.tfMp3.setForeground(Color.LIGHT_GRAY);
            
            MainFrame.tfVideoLoop.setBackground(Color.DARK_GRAY);
            MainFrame.tfVideoLoop.setForeground(Color.LIGHT_GRAY);
            
            // -------------------------------------------------------------------------------------- LABELS
            MainFrame.lblBGM1.setBackground(Color.DARK_GRAY);
            MainFrame.lblBGM1.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblBGM1.setOpaque(true);
            
            MainFrame.lblBGM2.setBackground(Color.DARK_GRAY);
            MainFrame.lblBGM2.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblBGM2.setOpaque(true);
            
            MainFrame.lblVolBGM1.setBackground(Color.DARK_GRAY);
            MainFrame.lblVolBGM1.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblVolBGM1.setOpaque(true);
            
            MainFrame.lblVolBGM2.setBackground(Color.DARK_GRAY);
            MainFrame.lblVolBGM2.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblVolBGM2.setOpaque(true);
            
            MainFrame.lblLastOperation.setBackground(Color.DARK_GRAY);
            MainFrame.lblLastOperation.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblLastOperation.setOpaque(true);
            
            MainFrame.lblCurrentMp3Duration.setBackground(Color.DARK_GRAY);
            MainFrame.lblCurrentMp3Duration.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblCurrentMp3Duration.setOpaque(true);
            
            MainFrame.lblLinkBGMVolumes.setBackground(Color.DARK_GRAY);
            MainFrame.lblLinkBGMVolumes.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblLinkBGMVolumes.setOpaque(true);
                    
            MainFrame.lblVolSFX.setBackground(Color.DARK_GRAY);
            MainFrame.lblVolSFX.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblVolSFX.setOpaque(true);
            
            MainFrame.lblSFXState.setBackground(Color.DARK_GRAY);
            MainFrame.lblSFXState.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblSFXState.setOpaque(true);
            
            MainFrame.lblVideoLoop.setBackground(Color.DARK_GRAY);
            MainFrame.lblVideoLoop.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblVideoLoop.setOpaque(true);
            
            MainFrame.lblMp3.setBackground(Color.DARK_GRAY);
            MainFrame.lblMp3.setForeground(Color.LIGHT_GRAY);
            MainFrame.lblMp3.setOpaque(true);
            
            MainFrame.lblR1SFX01.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX01.setForeground(Color.WHITE);
            MainFrame.lblR1SFX01.setOpaque(true);
            
            MainFrame.lblR1SFX02.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX02.setForeground(Color.WHITE);
            MainFrame.lblR1SFX02.setOpaque(true);
            
            MainFrame.lblR1SFX03.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX03.setForeground(Color.WHITE);
            MainFrame.lblR1SFX03.setOpaque(true);
            
            MainFrame.lblR1SFX04.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX04.setForeground(Color.WHITE);
            MainFrame.lblR1SFX04.setOpaque(true);
            
            MainFrame.lblR1SFX05.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX05.setForeground(Color.WHITE);
            MainFrame.lblR1SFX05.setOpaque(true);
            
            MainFrame.lblR1SFX06.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX06.setForeground(Color.WHITE);
            MainFrame.lblR1SFX06.setOpaque(true);
            
            MainFrame.lblR1SFX07.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX07.setForeground(Color.WHITE);
            MainFrame.lblR1SFX07.setOpaque(true);
            
            MainFrame.lblR1SFX08.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX08.setForeground(Color.WHITE);
            MainFrame.lblR1SFX08.setOpaque(true);
            
            MainFrame.lblR1SFX09.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX09.setForeground(Color.WHITE);
            MainFrame.lblR1SFX09.setOpaque(true);
            
            MainFrame.lblR1SFX10.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX10.setForeground(Color.WHITE);
            MainFrame.lblR1SFX10.setOpaque(true);
            
            MainFrame.lblR1SFX11.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX11.setForeground(Color.WHITE);
            MainFrame.lblR1SFX11.setOpaque(true);
            
            MainFrame.lblR1SFX12.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX12.setForeground(Color.WHITE);
            MainFrame.lblR1SFX12.setOpaque(true);
            
            MainFrame.lblR1SFX13.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX13.setForeground(Color.WHITE);
            MainFrame.lblR1SFX13.setOpaque(true);
            
            MainFrame.lblR1SFX14.setBackground(Color.DARK_GRAY);
            MainFrame.lblR1SFX14.setForeground(Color.WHITE);
            MainFrame.lblR1SFX14.setOpaque(true);
            
            MainFrame.lblR2SFX01.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX01.setForeground(Color.WHITE);
            MainFrame.lblR2SFX01.setOpaque(true);
            
            MainFrame.lblR2SFX02.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX02.setForeground(Color.WHITE);
            MainFrame.lblR2SFX02.setOpaque(true);
            
            MainFrame.lblR2SFX03.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX03.setForeground(Color.WHITE);
            MainFrame.lblR2SFX03.setOpaque(true);
            
            MainFrame.lblR2SFX04.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX04.setForeground(Color.WHITE);
            MainFrame.lblR2SFX04.setOpaque(true);
            
            MainFrame.lblR2SFX05.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX05.setForeground(Color.WHITE);
            MainFrame.lblR2SFX05.setOpaque(true);
            
            MainFrame.lblR2SFX06.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX06.setForeground(Color.WHITE);
            MainFrame.lblR2SFX06.setOpaque(true);
            
            MainFrame.lblR2SFX07.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX07.setForeground(Color.WHITE);
            MainFrame.lblR2SFX07.setOpaque(true);
            
            MainFrame.lblR2SFX08.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX08.setForeground(Color.WHITE);
            MainFrame.lblR2SFX08.setOpaque(true);
            
            MainFrame.lblR2SFX09.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX09.setForeground(Color.WHITE);
            MainFrame.lblR2SFX09.setOpaque(true);
            
            MainFrame.lblR2SFX10.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX10.setForeground(Color.WHITE);
            MainFrame.lblR2SFX10.setOpaque(true);
            
            MainFrame.lblR2SFX11.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX11.setForeground(Color.WHITE);
            MainFrame.lblR2SFX11.setOpaque(true);
            
            MainFrame.lblR2SFX12.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX12.setForeground(Color.WHITE);
            MainFrame.lblR2SFX12.setOpaque(true);
            
            MainFrame.lblR2SFX13.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX13.setForeground(Color.WHITE);
            MainFrame.lblR2SFX13.setOpaque(true);
            
            MainFrame.lblR2SFX14.setBackground(Color.DARK_GRAY);
            MainFrame.lblR2SFX14.setForeground(Color.WHITE);
            MainFrame.lblR2SFX14.setOpaque(true);
            
            MainFrame.lblR3SFX01.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX01.setForeground(Color.WHITE);
            MainFrame.lblR3SFX01.setOpaque(true);
            
            MainFrame.lblR3SFX02.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX02.setForeground(Color.WHITE);
            MainFrame.lblR3SFX02.setOpaque(true);
            
            MainFrame.lblR3SFX03.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX03.setForeground(Color.WHITE);
            MainFrame.lblR3SFX03.setOpaque(true);
            
            MainFrame.lblR3SFX04.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX04.setForeground(Color.WHITE);
            MainFrame.lblR3SFX04.setOpaque(true);
            
            MainFrame.lblR3SFX05.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX05.setForeground(Color.WHITE);
            MainFrame.lblR3SFX05.setOpaque(true);
            
            MainFrame.lblR3SFX06.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX06.setForeground(Color.WHITE);
            MainFrame.lblR3SFX06.setOpaque(true);
            
            MainFrame.lblR3SFX07.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX07.setForeground(Color.WHITE);
            MainFrame.lblR3SFX07.setOpaque(true);
            
            MainFrame.lblR3SFX08.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX08.setForeground(Color.WHITE);
            MainFrame.lblR3SFX08.setOpaque(true);
            
            MainFrame.lblR3SFX09.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX09.setForeground(Color.WHITE);
            MainFrame.lblR3SFX09.setOpaque(true);
            
            MainFrame.lblR3SFX10.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX10.setForeground(Color.WHITE);
            MainFrame.lblR3SFX10.setOpaque(true);
            
            MainFrame.lblR3SFX11.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX11.setForeground(Color.WHITE);
            MainFrame.lblR3SFX11.setOpaque(true);
            
            MainFrame.lblR3SFX12.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX12.setForeground(Color.WHITE);
            MainFrame.lblR3SFX12.setOpaque(true);
            
            MainFrame.lblR3SFX13.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX13.setForeground(Color.WHITE);
            MainFrame.lblR3SFX13.setOpaque(true);
            
            MainFrame.lblR3SFX14.setBackground(Color.DARK_GRAY);
            MainFrame.lblR3SFX14.setForeground(Color.WHITE);
            MainFrame.lblR3SFX14.setOpaque(true);
            
            // -------------------------------------------------------------------------------------- CHECK BOX
            MainFrame.chkLoop1.setBackground(Color.DARK_GRAY);
            MainFrame.chkLoop1.setForeground(Color.LIGHT_GRAY);
//            MainFrame.chkLoop1.setBorder(new LineBorder(Color.LIGHT_GRAY, 0));
            
            MainFrame.chkLoop2.setBackground(Color.DARK_GRAY);
            MainFrame.chkLoop2.setForeground(Color.LIGHT_GRAY);
            
            MainFrame.chkLoopVL.setBackground(Color.DARK_GRAY);
            MainFrame.chkLoopVL.setForeground(Color.LIGHT_GRAY);
            
            MainFrame.chkMuteVL.setBackground(Color.DARK_GRAY);
            MainFrame.chkMuteVL.setForeground(Color.LIGHT_GRAY);
            
            MainFrame.chkFitVL.setBackground(Color.DARK_GRAY);
            MainFrame.chkFitVL.setForeground(Color.LIGHT_GRAY);
            
            MainFrame.chkVLMode.setBackground(Color.DARK_GRAY);
            MainFrame.chkVLMode.setForeground(Color.LIGHT_GRAY);
            
            // -------------------------------------------------------------------------------------- RADIO BUTTON
            MainFrame.chkSP.setBackground(Color.DARK_GRAY);
            MainFrame.chkSP.setForeground(Color.LIGHT_GRAY);
            
            MainFrame.chkIB.setBackground(Color.DARK_GRAY);
            MainFrame.chkIB.setForeground(Color.LIGHT_GRAY);
            
            // -------------------------------------------------------------------------------------- VOLUME BAR
            MainFrame.volBGM1.setBackground(Color.DARK_GRAY);
            MainFrame.volBGM2.setBackground(Color.DARK_GRAY);
            MainFrame.volSFX.setBackground(Color.DARK_GRAY);
        }
        else {
            HappyButtons.uiTheme = "light";
            
            UIProfile ui = new UIProfile();
            HappyButtons.dbo.autoSaveUISettings(HappyButtons.uiDB, ui);

            SystemClass.UITheme(HappyButtons.uiTheme);
        }
        
        // ------------------------------------------------------------------------ MP3 UI
        if(MainFrame.mp3FrameOpened == 1) {
            if(MainFrame.mp3 != null) {
                MainFrame.mp3.dispose();
            }
//            MainFrame.mp3 = null;
            MainFrame.mp3 = new Mp3Frame();
            MainFrame.mp3.setVisible(true);
        }
    }
    
    public static void themeUIFrame(String theme) {
        if(theme.equals("dark")) { // System.out.print(1);
            UIThemeFrame.panel1.setBackground(Color.DARK_GRAY);
            
            UIThemeFrame.panelBox.setBackground(Color.DARK_GRAY);
            UIThemeFrame.panelBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
    }
    
    public static void setupElementsStatus() {
        // VL Play button
        if(!(HappyButtons.vlcjPath).equals("")) {
            (MainFrame.btnPlayVL).setEnabled(true);
        }
        else {
            (MainFrame.btnPlayVL).setEnabled(false);
        }
        
        // AS - autosave indicator
        if(MainFrame.enableAutosave.equals("on")) {
            MainFrame.itmAS.setForeground(Color.GREEN);
            MainFrame.itmAS.setVisible(true);
        }
        else {
            MainFrame.itmAS.setVisible(false);
        }
    }
}
