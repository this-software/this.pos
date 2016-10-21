package dis.software.pos.table.cell.renderer;

import dis.software.pos.EntityStatus;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Clase para construir y personalizar una columna de tipo EntityStatus
 * @author Milton Cavazos
 */
public class EntityStatusCellRenderer extends DefaultTableCellRenderer
{
    
    public EntityStatusCellRenderer()
    {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        super.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        EntityStatus status = (EntityStatus) value;
        if (status == EntityStatus.ACTIVE)
        {
            super.setIcon(new DynamicIcon(Color.GREEN));
            super.setText("ACTIVO");
            return this;
        }
        super.setIcon(new DynamicIcon(Color.RED));
        super.setText("INACTIVO");
        return this;
    }
    
}

class DynamicIcon implements Icon
{
    
    Color color = Color.BLACK;

    public DynamicIcon(Color color)
    {
        this.color = color;
    }
    
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        int r = 16 / 2;
        g2d.fillOval(x + 8 - r, y + 8 - r, 16, 16);
    }

    @Override
    public int getIconWidth()
    {
        return 16;
    }

    @Override
    public int getIconHeight()
    {
        return 16;
    }
    
}
