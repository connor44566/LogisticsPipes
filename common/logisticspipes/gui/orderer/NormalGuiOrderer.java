package logisticspipes.gui.orderer;

import logisticspipes.network.NetworkConstants;
import logisticspipes.network.packets.PacketPipeInteger;
import logisticspipes.proxy.MainProxy;
import logisticspipes.utils.ItemIdentifier;
import logisticspipes.utils.gui.SmallGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

public class NormalGuiOrderer extends GuiOrderer {

	private enum DisplayOptions {
		Both,
		SupplyOnly,
		CraftOnly,
	}

	protected DisplayOptions displayOptions = DisplayOptions.Both;
	
	public NormalGuiOrderer(int x, int y, int z, int dim, EntityPlayer entityPlayer) {
		super(x, y, z, dim, entityPlayer);
		refreshItems();
	}
	
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();
		controlList.add(new SmallGuiButton(3, guiLeft + 10, bottom - 15, 46, 10, "Refresh")); // Refresh
		controlList.add(new SmallGuiButton(13,  guiLeft + 10, bottom - 28, 46, 10, "Content")); // Component
		controlList.add(new SmallGuiButton(9, guiLeft + 10, bottom - 41, 46, 10, "Both"));
	}
	
	public void refreshItems(){
			int integer;
			switch(displayOptions) {
			case Both:
				integer = 0;
				break;
			case SupplyOnly:
				integer = 1;
				break;
			case CraftOnly:
				integer = 2;
				break;
			default: 
				integer = 3;
			}
			integer += (dimension * 10);
			MainProxy.sendPacketToServer(new PacketPipeInteger(NetworkConstants.ORDERER_REFRESH_REQUEST,xCoord,yCoord,zCoord,integer).getPacket());
	}

	protected void actionPerformed(GuiButton guibutton) {
		super.actionPerformed(guibutton);
		if (guibutton.id == 9) {
			String displayString = "";
			switch (displayOptions){
			case Both:
				displayOptions = DisplayOptions.CraftOnly;
				displayString = "Craft";
				break;
			case CraftOnly:
				displayOptions = DisplayOptions.SupplyOnly;
				displayString = "Supply";
				break;
			case SupplyOnly:
				displayOptions = DisplayOptions.Both;
				displayString = "Both";
				break;
			}
			guibutton.displayString = displayString;
			refreshItems();
		}
	}

	@Override
	public void specialItemRendering(ItemIdentifier item, int x, int y) {}
}
