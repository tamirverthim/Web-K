/*
 * {{{ header & license
 * Copyright (c) 2004, 2005 Joshua Marinacci
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package com.earnix.webk.swing;


import com.earnix.webk.context.StyleReference;
import com.earnix.webk.css.constants.ValueConstants;
import com.earnix.webk.layout.SharedContext;
import com.earnix.webk.runtime.dom.impl.CommentImpl;
import com.earnix.webk.runtime.dom.impl.ElementImpl;
import com.earnix.webk.runtime.dom.impl.NodeImpl;
import com.earnix.webk.runtime.html.impl.DocumentImpl;
import com.earnix.webk.runtime.dom.impl.TextImpl;
import org.w3c.dom.css.CSSPrimitiveValue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DOMInspector extends JPanel {
    private static final long serialVersionUID = 1L;

    // PW
    StyleReference styleReference;
    SharedContext context;
    ElementPropertiesPanel elementPropPanel;
    DOMSelectionListener nodeSelectionListener;
    JSplitPane splitPane;
    // PW

    DocumentImpl doc;
    JButton close;
    JTree tree;
    JScrollPane scroll;

    public DOMInspector(DocumentImpl doc) {
        this(doc, null, null);
    }

    public DOMInspector(DocumentImpl doc, SharedContext context, StyleReference sr) {
        super();

        this.setLayout(new java.awt.BorderLayout());

        //JPanel treePanel = new JPanel();
        this.tree = new JTree();
        this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.scroll = new JScrollPane(tree);

        splitPane = null;
        if (sr == null) {
            add(scroll, "Center");
        } else {
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setOneTouchExpandable(true);
            splitPane.setDividerLocation(150);

            this.add(splitPane, "Center");
            splitPane.setLeftComponent(scroll);
        }

        close = new JButton("close");
        this.add(close, "South");
        this.setPreferredSize(new Dimension(300, 300));

        setForDocument(doc, context, sr);

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                getFrame(DOMInspector.this).setVisible(false);
            }
        });
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawLine(0, 0, 100, 100);

    }

    /**
     * Sets the forDocument attribute of the DOMInspector object
     *
     * @param doc The new forDocument value
     */
    public void setForDocument(DocumentImpl doc) {
        setForDocument(doc, null, null);
    }

    /**
     * Sets the forDocument attribute of the DOMInspector object
     *
     * @param doc     The new forDocument value
     * @param context The new forDocument value
     * @param sr      The new forDocument value
     */
    public void setForDocument(DocumentImpl doc, SharedContext context, StyleReference sr) {
        this.doc = doc;
        this.styleReference = sr;
        this.context = context;
        this.initForCurrentDocument();
    }

    /**
     * Gets the frame attribute of the DOMInspector object
     *
     * @param comp PARAM
     * @return The frame value
     */
    public JFrame getFrame(Component comp) {
        if (comp instanceof JFrame) {
            return (JFrame) comp;
        }
        return getFrame(comp.getParent());
    }

    private void initForCurrentDocument() {
        // tree stuff
        TreeModel model = new DOMTreeModel(doc);
        tree.setModel(model);
        if (!(tree.getCellRenderer() instanceof DOMTreeCellRenderer)) {
            tree.setCellRenderer(new DOMTreeCellRenderer());
        }

        if (styleReference != null) {
            if (elementPropPanel != null) {
                splitPane.remove(elementPropPanel);
            }
            elementPropPanel = new ElementPropertiesPanel(styleReference);
            splitPane.setRightComponent(elementPropPanel);

            tree.removeTreeSelectionListener(nodeSelectionListener);

            //nodeSelectionListener = new DOMSelectionListener( tree, styleReference, elementPropPanel );
            nodeSelectionListener = new DOMSelectionListener(tree, elementPropPanel);
            tree.addTreeSelectionListener(nodeSelectionListener);
        }
    }
}

//-{{{ ElementPropertiesPanel

class ElementPropertiesPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private StyleReference _sr;
    private JTable _properties;
    private TableModel _defaultTableModel;

    ElementPropertiesPanel(StyleReference sr) {
        super();
        //this._context = context;
        this._sr = sr;

        this._properties = new PropertiesJTable();
        this._defaultTableModel = new DefaultTableModel();

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(_properties), BorderLayout.CENTER);
    }

    /**
     * Sets the forElement attribute of the ElementPropertiesPanel object
     *
     * @param node The new forElement value
     */
    public void setForElement(NodeImpl node) {
        try {
            _properties.setModel(tableModel(node));
            TableColumnModel model = _properties.getColumnModel();
            if (model.getColumnCount() > 0) {
                model.getColumn(0).sizeWidthToFit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private TableModel tableModel(NodeImpl node) {
        if (!(node instanceof ElementImpl)) {
            Toolkit.getDefaultToolkit().beep();
            return _defaultTableModel;
        }
        Map props = _sr.getCascadedPropertiesMap((ElementImpl) node);
        return new PropertiesTableModel(props);
    }

    static class PropertiesJTable extends JTable {
        private static final long serialVersionUID = 1L;

        Font propLabelFont;
        Font defaultFont;

        PropertiesJTable() {
            super();
            this.setColumnSelectionAllowed(false);
            this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            propLabelFont = new Font("Courier New", Font.BOLD, 12);
            defaultFont = new Font("Default", Font.PLAIN, 12);
        }

        /**
         * Gets the cellRenderer attribute of the PropertiesJTable object
         *
         * @param row PARAM
         * @param col PARAM
         * @return The cellRenderer value
         */
        public TableCellRenderer getCellRenderer(int row, int col) {
            JLabel label = (JLabel) super.getCellRenderer(row, col);
            label.setBackground(Color.white);
            label.setFont(defaultFont);
            if (col == 0) {
                // BUG: not working?
                label.setFont(propLabelFont);
            } else if (col == 2) {
                PropertiesTableModel pmodel = (PropertiesTableModel) this.getModel();
                Map.Entry me = (Map.Entry) pmodel._properties.entrySet().toArray()[row];
                CSSPrimitiveValue cpv = (CSSPrimitiveValue) me.getValue();
                if (cpv.getCssText().startsWith("rgb")) {
                    label.setBackground(com.earnix.webk.css.util.ConversionUtil.rgbToColor(cpv.getRGBColorValue()));
                }
            }
            return (TableCellRenderer) label;
        }
    }

    /**
     * @author Patrick Wright
     */
    static class PropertiesTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;

        //String _colNames[] = {"Property Name", "Text", "Value", "Important-Inherit"};
        String _colNames[] = {"Property Name", "Text", "Value"};

        Map _properties;

        PropertiesTableModel(Map cssProperties) {
            _properties = cssProperties;
        }

        /**
         * Gets the columnName attribute of the PropertiesTableModel object
         *
         * @param col PARAM
         * @return The columnName value
         */
        public String getColumnName(int col) {
            return _colNames[col];
        }

        /**
         * Gets the columnCount attribute of the PropertiesTableModel object
         *
         * @return The columnCount value
         */
        public int getColumnCount() {
            return _colNames.length;
        }

        /**
         * Gets the rowCount attribute of the PropertiesTableModel object
         *
         * @return The rowCount value
         */
        public int getRowCount() {
            return _properties.size();
        }

        /**
         * Gets the valueAt attribute of the PropertiesTableModel object
         *
         * @param row PARAM
         * @param col PARAM
         * @return The valueAt value
         */
        public Object getValueAt(int row, int col) {
            Map.Entry me = (Map.Entry) _properties.entrySet().toArray()[row];
            CSSPrimitiveValue cpv = (CSSPrimitiveValue) me.getValue();

            Object val = null;
            switch (col) {

                case 0:
                    val = me.getKey();
                    break;
                case 1:
                    val = cpv.getCssText();
                    break;
                case 2:
                    if (ValueConstants.isNumber(cpv.getPrimitiveType())) {
                        val = new Float(cpv.getFloatValue(cpv.getPrimitiveType()));
                    } else {
                        val = "";//actual.cssValue().getCssText();
                    }
                    break;
                    /* ouch, can't do this now: case 3:
                        val = ( cpv.actual.isImportant() ? "!Imp" : "" ) +
                                " " +
                                ( actual.forcedInherit() ? "Inherit" : "" );
                        break;
                     */
            }
            return val;
        }

        /**
         * Gets the cellEditable attribute of the PropertiesTableModel object
         *
         * @param row PARAM
         * @param col PARAM
         * @return The cellEditable value
         */
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
}//}}}

//-{{{ DOMSelectionListener

class DOMSelectionListener implements TreeSelectionListener {
    private JTree _tree;
    //private StyleReference _sr;
    private ElementPropertiesPanel _elemPropPanel;

    //DOMSelectionListener( JTree tree, StyleReference sr, ElementPropertiesPanel panel ) {
    DOMSelectionListener(JTree tree, ElementPropertiesPanel panel) {
        _tree = tree;
        //_sr = sr;
        _elemPropPanel = panel;
    }

    public void valueChanged(TreeSelectionEvent e) {
        NodeImpl node = (NodeImpl) _tree.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }

        _elemPropPanel.setForElement(node);
    }
}//}}}

//-{{{

class DOMTreeModel implements TreeModel {

    DocumentImpl doc;

    /**
     * Our root for display
     */
    NodeImpl root;
    HashMap displayableNodes;
    List listeners = new ArrayList();

    public DOMTreeModel(DocumentImpl doc) {
        this.displayableNodes = new HashMap();
        this.doc = doc;
        setRoot("body");
    }

    private void setRoot(String rootNodeName) {
        NodeImpl tempRoot = doc;
        List<NodeImpl> nl = (tempRoot).getChildNodes();
        for (int i = 0; i < nl.size(); i++) {
            if (nl.get(i).nodeName().toLowerCase().equals(rootNodeName)) {
                this.root = nl.get(i);
            }
        }
    }


    //Adds a listener for the TreeModelEvent posted after the tree changes.

    /**
     * Adds the specified TreeModel listener to receive TreeModel events from
     * this component. If listener l is null, no exception is thrown and no
     * action is performed.
     *
     * @param l Contains the TreeModelListener for TreeModelEvent data.
     */
    public void addTreeModelListener(TreeModelListener l) {

        this.listeners.add(l);

    }


    //Removes a listener previously added with addTreeModelListener.

    /**
     * Removes the specified TreeModel listener so that it no longer receives
     * TreeModel events from this component. This method performs no function,
     * nor does it throw an exception, if the listener specified by the argument
     * was not previously added to this component. If listener l is null, no
     * exception is thrown and no action is performed.
     *
     * @param l Contains the TreeModelListener for TreeModelEvent data.
     */
    public void removeTreeModelListener(TreeModelListener l) {

        this.listeners.remove(l);

    }


    //Messaged when the user has altered the value for the item identified by path to newValue.
    public void valueForPathChanged(TreePath path, Object newValue) {

        // no-op

    }

    //Returns the child of parent at index index in the parent's child array.

    /**
     * Gets the child attribute of the DOMTreeModel object
     *
     * @param parent PARAM
     * @param index  PARAM
     * @return The child value
     */
    public Object getChild(Object parent, int index) {

        NodeImpl node = (NodeImpl) parent;

        List children = (List) this.displayableNodes.get(parent);
        if (children == null) {
            children = addDisplayable(node);
        }

        return (NodeImpl) children.get(index);
    }


    //Returns the number of children of parent.

    /**
     * Gets the childCount attribute of the DOMTreeModel object
     *
     * @param parent PARAM
     * @return The childCount value
     */
    public int getChildCount(Object parent) {

        NodeImpl node = (NodeImpl) parent;
        List children = (List) this.displayableNodes.get(parent);
        if (children == null) {
            children = addDisplayable(node);
        }

        return children.size();
    }


    //Returns the index of child in parent.

    /**
     * Gets the indexOfChild attribute of the DOMTreeModel object
     *
     * @param parent PARAM
     * @param child  PARAM
     * @return The indexOfChild value
     */
    public int getIndexOfChild(Object parent, Object child) {

        NodeImpl node = (NodeImpl) parent;
        List children = (List) this.displayableNodes.get(parent);
        if (children == null) {
            children = addDisplayable(node);
        }
        if (children.contains(child)) {
            return children.indexOf(child);
        } else {
            return -1;
        }
    }


    //Returns the root of the tree.

    /**
     * Gets the root attribute of the DOMTreeModel object
     *
     * @return The root value
     */
    public Object getRoot() {

        return this.root;
    }


    //Returns true if node is a leaf.

    /**
     * Gets the leaf attribute of the DOMTreeModel object
     *
     * @param nd PARAM
     * @return The leaf value
     */
    public boolean isLeaf(Object nd) {

        NodeImpl node = (NodeImpl) nd;

        return node.childNodeSize() == 0;
    }

    // only adds displayable nodes--not stupid DOM text filler nodes

    /**
     * Adds a feature to the Displayable attribute of the DOMTreeModel object
     *
     * @param parent The feature to be added to the Displayable attribute
     * @return Returns
     */
    private List addDisplayable(NodeImpl parent) {
        List children = (List) this.displayableNodes.get(parent);
        if (children == null) {
            children = new ArrayList();
            this.displayableNodes.put(parent, children);
            List<NodeImpl> nl = parent.getChildNodes();
            for (int i = 0, len = nl.size(); i < len; i++) {
                NodeImpl child = nl.get(i);
                if (child instanceof ElementImpl ||
                        child instanceof CommentImpl ||
                        (child instanceof TextImpl && (((TextImpl) child).getWholeText().trim().length() > 0))) {
                    children.add(child);
                }
            }
            return children;
        } else {
            return new ArrayList();
        }
    }

}//}}}

//-{{{ DOMTreeCellRenderer

class DOMTreeCellRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;

    /**
     * Gets the treeCellRendererComponent attribute of the DOMTreeCellRenderer
     * object
     *
     * @param tree     PARAM
     * @param value    PARAM
     * @param selected PARAM
     * @param expanded PARAM
     * @param leaf     PARAM
     * @param row      PARAM
     * @param hasFocus PARAM
     * @return The treeCellRendererComponent value
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        NodeImpl node = (NodeImpl) value;

        if (node instanceof ElementImpl) {

            String cls = "";
            if (node.getAttributes().size() > 0) {
                String cn = node.getAttributes().get("class");
                if (cn != null) {
                    cls = " class='" + cn + "'";
                }
            }
            value = "<" + node.nodeName() + cls + ">";

        }

        if (node instanceof TextImpl) {

            if (((TextImpl) node).getWholeText().trim().length() > 0) {
                value = "\"" + ((TextImpl) node).getWholeText() + "\"";
            }
        }

        if (node instanceof CommentImpl) {

            value = "<!-- " + ((CommentImpl) node).getData() + " -->";

        }

        DefaultTreeCellRenderer tcr = (DefaultTreeCellRenderer) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        tcr.setOpenIcon(null);
        tcr.setClosedIcon(null);

        return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    }
}//}}}

