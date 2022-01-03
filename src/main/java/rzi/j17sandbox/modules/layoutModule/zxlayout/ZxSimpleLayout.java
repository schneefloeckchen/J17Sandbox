package rzi.j17sandbox.modules.layoutModule.zxlayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.LinkedList;
import java.util.List;
import javax.naming.InitialContext;

/**
 * Form eines Flow Layouts. Die neuen Elemente werden der Reihe nach im Layout
 * eingebaut,
 * dabei ist ein 'New Line' moeglich.
 *
 * @author rene
 */
public class ZxSimpleLayout implements LayoutManager {

    public static final String INITIAL = "INITIAL";      // Der erste Eintrag ueberhaupt
    public static final String NEXT = "NEXT";
    public static final String NEW_LINE = "NEW_LINE";

    private List<LayoutElement> layoutElements = new LinkedList();
    private int gap = 5;        // Gap between elements
    private int m_parentWidth = 0;      // To determine the width of the parent
    private int m_parentHeight = 0;     // container

    @Override
    public void addLayoutComponent(String string, Component cmpnt) {
        switch (string) {
            case "INITIAL":
                layoutElements = new LinkedList<>();
            default:
                layoutElements.add(new LayoutElement(string, cmpnt));
        }
        System.out.println("addLayoutComponent -- " + string + " added");
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        System.out.println("removeLayoutComponent not implemented - parameter: " + comp.toString());
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        System.out.println("preferredLayoutSize");
        layoutContainer(parent);
        return new Dimension(m_parentWidth, m_parentHeight);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        System.out.println("minimumLayoutSize");
        layoutContainer(parent);
        return new Dimension(m_parentWidth, m_parentHeight);
    }

    @Override
    public void layoutContainer(Container parent) {
        System.out.println("layoutContainer");
        int locationX = gap;
        int locationY = gap;
        int lastHeight = gap;      // zwischenspeicher f. hoehe letztes Element
        for (LayoutElement element : layoutElements) {
            Component c = element.component;
            c.setSize(c.getPreferredSize());
            int cWidth = c.getWidth();
            int cHeight = c.getHeight();
            String constraint = element.control;
            switch (constraint) {
                case INITIAL -> {
                    locationX = gap;
                    locationY = gap;
                    c.setLocation(locationX, locationY);
                    locationX += cWidth + gap;
                }
                case NEXT -> {
                    c.setLocation(locationX, locationY);
                    locationX += cWidth + gap;
                }
                case NEW_LINE -> {
                    locationX = gap;
                    locationY += lastHeight + gap;
                    c.setLocation(locationX, locationY);
                    locationX += cWidth + gap;
                }
            }
            lastHeight = cHeight;
            m_parentWidth = Integer.max (m_parentWidth, locationX);
            m_parentHeight = Integer.max (m_parentHeight, locationY + lastHeight + gap);
            System.out.println("Width = "+m_parentWidth + "   Hight = "+m_parentHeight);
        }
    }

    private class LayoutElement {

        Component component;
        String control;

        public LayoutElement(String control, Component component) {
            this.control = control;
            this.component = component;
        }
    }
}
