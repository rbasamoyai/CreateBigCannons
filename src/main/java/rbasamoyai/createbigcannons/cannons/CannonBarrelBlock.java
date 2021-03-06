package rbasamoyai.createbigcannons.cannons;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.utility.VoxelShaper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CannonBarrelBlock extends CannonTubeBlock {

	private static final EnumProperty<Axis> AXIS = RotatedPillarBlock.AXIS;
	
	private final VoxelShaper shapes;
	
	public CannonBarrelBlock(Properties properties, CannonMaterial material) {
		super(properties, material);
		this.shapes = this.makeShapes();
	}
	
	protected VoxelShaper makeShapes() {
		VoxelShape base = Block.box(2, 0, 2, 14, 16, 14);
		return new AllShapes.Builder(base).forAxis();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
		return this.shapes.get(state.getValue(AXIS));
	}
	
}
