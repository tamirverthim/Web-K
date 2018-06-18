//package org.xhtmlrenderer.simple;
//
//import org.w3c.dom.Document;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//
///**
// * @author Taras Maslov
// * 5/21/2018
// */
//public class Browser {
//    private Window window;
//
//    public void start() {
//        
//        window = new Window();
//        JFrame frame = new JFrame();
//
//        FSScrollPane scroll = new FSScrollPane(window.getPanel());
//
//        frame = new JFrame("Flying Saucer JS");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(scroll, BorderLayout.CENTER);
//        final JTextField address = new JTextField();
//        address.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                window.loadUrl(address.getText());
//            }
//        });
//        frame.getContentPane().add(address, BorderLayout.NORTH);
//        frame.pack();
//        frame.setSize(1024, 768);
//        frame.setVisible(true);
//    }
//
//    private void launchLoad(String url) {
//        new Thread(new Runnable() {
//            public void run() {
//                final Document doc;
//                try {
//                    if (window.getPanel()!= null ) window.getPanel().setCursor(new Cursor(Cursor.WAIT_CURSOR));
//                    doc = window.getPanel().getUAC().getXMLResource(url).getDocument();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    System.err.println("Can't load document");
//                    return;
//                } finally {
//                    if (panel != null ) panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//                }
//                EventQueue.invokeLater(new Runnable() {
//                    public void run() {
//                        startRender(doc);
//                    }
//                });
//
//            }
//        }).start();
//    }
//}
