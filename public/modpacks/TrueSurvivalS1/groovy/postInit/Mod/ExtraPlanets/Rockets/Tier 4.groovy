import gregtech.api.metatileentity.multiblock.CleanroomType


if (isLoaded("extraplanets")) {

	// --- Массивы

	def rocket = [
		item('extraplanets:item_tier4_rocket'),
		item('extraplanets:item_tier4_rocket:1'),
		item('extraplanets:item_tier4_rocket:2'),
		item('extraplanets:item_tier4_rocket:3'),
	]
	def crate = [
		metaitem('plateAluminium'),
	   	metaitem('crate.aluminium'),
	   	metaitem('crate.stainless_steel'),
	   	metaitem('crate.titanium')
	]

	// --- Добавление рецептов

	for (int i = 0; i < rocket.size(); i++) {
	// Ракета
	assembly_line.recipeBuilder()
		.inputs(item('extraplanets:nose_cone_tier4'))
		.inputs(metaitem('rocket.body.tier.4') * 12)
		.inputs(item('extraplanets:tier4_items:2') * 6)
		.inputs(item('extraplanets:tier4_items:1') * 4)
		.inputs(item('extraplanets:tier4_items') * 2)
		.inputs(item('galacticraftcore:oil_canister_partial:1001') * 16)
		.inputs(metaitem('lander.tier.3'))
		.inputs(metaitem('electric.motor.luv') * 4)
		.inputs(metaitem('emitter.luv') * 4)
		.inputs(ore('circuitLuv') * 8)
		.inputs(crate[i] * 12)
		.inputs(metaitem('rocket.control.computer.tier.4'))
		.inputs(item('extraplanets:schematic_tier4'))
		.fluidInputs(fluid('dense_ice') * 9216)
		.fluidInputs(fluid('soldering_alloy') * 4608)
		.fluidInputs(fluid('ruridit') * 4608)
		.outputs(rocket[i])
		.duration(1200).EUt(16384).buildAndRegister()
	}
	// Ракетный компьютер
	assembler.recipeBuilder()
		.circuitMeta(4)
		.inputs(
			item('opencomputers:case2'),
			item('opencomputers:keyboard'),
			item('opencomputers:screen2'),
			metaitem('emitter.luv'),
			metaitem('sensor.luv'),
			item('extraplanets:tier4_items:3'))
		.fluidInputs(fluid('soldering_alloy') * 1728)
		.outputs(metaitem('rocket.control.computer.tier.4'))
		.cleanroom(CleanroomType.CLEANROOM)
		.duration(600).EUt(16384).buildAndRegister()
	// Головоной обтекатель
	assembler.recipeBuilder()
		.circuitMeta(4)
		.inputs(
			item('galacticraftplanets:heavy_nose_cone'),
			item('extraplanets:tier4_items:3') * 4,
			metaitem('sensor.luv') * 2,
			metaitem('screwTungstenSteel') * 4)
		.fluidInputs(fluid('soldering_alloy') * 288)
		.outputs(item('extraplanets:nose_cone_tier4'))
		.cleanroom(CleanroomType.CLEANROOM)
		.duration(600).EUt(16384).buildAndRegister()
	// Корпус
	assembler.recipeBuilder()
		.circuitMeta(4)
		.inputs(
			item('extraplanets:tier4_items:3') * 2,
			metaitem('voltage_coil.luv') * 2,
			metaitem('field.generator.luv'))
		.fluidInputs(fluid('soldering_alloy') * 288)
		.outputs(metaitem('rocket.body.tier.4'))
		.cleanroom(CleanroomType.CLEANROOM)
		.duration(300).EUt(16384).buildAndRegister()
	// Стабилизаторы
	assembler.recipeBuilder()
		.circuitMeta(4)
		.inputs(
			item('extraplanets:tier4_items:3') * 2,
			item('galacticraftplanets:item_basic_asteroids:5') * 2,
			metaitem('screwRuridit') * 4)
		.fluidInputs(fluid('soldering_alloy') * 288)
		.outputs(item('extraplanets:tier4_items:2'))
		.cleanroom(CleanroomType.CLEANROOM)
		.duration(300).EUt(16384).buildAndRegister()
	// Ракетные двигатели
	assembler.recipeBuilder()
		.circuitMeta(4)
		.inputs(
			item('extraplanets:tier4_items:3') * 5,
			item('galacticraftplanets:item_basic_asteroids:1') * 2,
			item('galacticraftcore:engine:1') * 2,
			metaitem('electric.pump.luv') * 4,
			metaitem('cableGtSingleAnnealedCopper') * 16)
		.fluidInputs(fluid('soldering_alloy') * 288)
		.outputs(item('extraplanets:tier4_items'))
		.duration(300).EUt(16384).buildAndRegister()
	// Ускорители
	assembler.recipeBuilder()
		.circuitMeta(4)
		.inputs(
			item('galacticraftcore:engine:1'),
			item('extraplanets:tier4_items:3') * 4,
			metaitem('plateDenseIce') * 3)
		.fluidInputs(fluid('soldering_alloy') * 288)
		.outputs(item('extraplanets:tier4_items:1'))
		.cleanroom(CleanroomType.CLEANROOM)
		.duration(600).EUt(16384).buildAndRegister()
	// Сплав сверх-прочных пластин
	assembly_line.recipeBuilder()
		.inputs(
			item('galacticraftplanets:item_basic_asteroids:5'),
			metaitem('plateDenseDenseIce') * 3,
			metaitem('plateDenseDenseIce') * 3,
			metaitem('boltRuridit') * 4)
		.fluidInputs(fluid('indium') * 36)
		.outputs(metaitem('alloy.ingot.tier.4'))
		.duration(300).EUt(16384).buildAndRegister()
	// Сверх-прочные пластины
	implosion_compressor.recipeBuilder()
		.inputs(metaitem('alloy.ingot.tier.4'))
		.outputs(
	      item('extraplanets:tier4_items:3'),
	      metaitem('dustTinyRuridit') * 4)
		.explosivesAmount(24)
		.duration(20).EUt(30).buildAndRegister()
	// Схема ракеты
	laser_engraver.recipeBuilder()
		.inputs(metaitem('schematic.blank'))
		.notConsumable(metaitem('glass_lens.light_blue'))
		.outputs(item('extraplanets:schematic_tier4'))
		.cleanroom(CleanroomType.CLEANROOM)
		.duration(9000).EUt(16384).buildAndRegister()
}