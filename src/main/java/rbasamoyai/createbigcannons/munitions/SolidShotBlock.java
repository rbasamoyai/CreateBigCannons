package rbasamoyai.createbigcannons.munitions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.CBCEntityTypes;

public class SolidShotBlock extends ProjectileBlock {
	
	public SolidShotBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public AbstractCannonProjectile getProjectile(Level level, BlockState state, BlockPos pos, BlockEntity blockEntity) {
		return CBCEntityTypes.SHOT.get().create(level);
	}
	
}
