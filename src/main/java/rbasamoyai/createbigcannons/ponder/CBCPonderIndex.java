package rbasamoyai.createbigcannons.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.ponder.PonderLocalization;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;

import rbasamoyai.createbigcannons.CBCBlocks;
import rbasamoyai.createbigcannons.CBCItems;
import rbasamoyai.createbigcannons.CreateBigCannons;

public class CBCPonderIndex {

	private static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CreateBigCannons.MOD_ID);
	private static final CreateRegistrate REGISTRATE = CreateBigCannons.registrate();
	
	public static void register() {
		HELPER.forComponents(CBCBlocks.CANNON_MOUNT, CBCBlocks.YAW_CONTROLLER)
			.addStoryBoard("cannon_mount/assembly_and_use", CannonMountScenes::assemblyAndUse)
			.addStoryBoard("cannon_mount/firing_big_cannons", CannonMountScenes::firingBigCannons);
		
		HELPER.forComponents(CBCBlocks.CANNON_LOADER, CBCBlocks.RAM_HEAD, CBCBlocks.WORM_HEAD, AllBlocks.PISTON_EXTENSION_POLE)
			.addStoryBoard("cannon_loader/loading_big_cannons", CannonLoadingScenes::loadingBigCannons);
		
		HELPER.forComponents(CBCBlocks.CANNON_LOADER, CBCBlocks.POWDER_CHARGE)
			.addStoryBoard("munitions/cannon_loads", CannonLoadingScenes::cannonLoads, CBCPonderTags.MUNITIONS);
		
		HELPER.forComponents(CBCItems.IMPACT_FUZE, CBCItems.TIMED_FUZE, CBCBlocks.HE_SHELL, CBCBlocks.SHRAPNEL_SHELL)
			.addStoryBoard("munitions/fuzing_munitions", CannonLoadingScenes::fuzingMunitions, CBCPonderTags.MUNITIONS);
	}
	
	public static void registerTags() {
		PonderRegistry.TAGS.forTag(CBCPonderTags.OPERATING_CANNONS)
			.add(CBCBlocks.CANNON_MOUNT)
			.add(CBCBlocks.YAW_CONTROLLER)
			.add(CBCBlocks.CANNON_LOADER)
			.add(CBCBlocks.RAM_HEAD)
			.add(CBCBlocks.WORM_HEAD)
			.add(AllBlocks.PISTON_EXTENSION_POLE);
		PonderRegistry.TAGS.forTag(CBCPonderTags.MUNITIONS)
			.add(CBCBlocks.POWDER_CHARGE)
			.add(CBCBlocks.HE_SHELL)
			.add(CBCBlocks.SHRAPNEL_SHELL)
			.add(CBCItems.IMPACT_FUZE)
			.add(CBCItems.TIMED_FUZE);
	}
	
	public static void registerLang() {
		PonderLocalization.provideRegistrateLang(REGISTRATE);
	}
	
}
