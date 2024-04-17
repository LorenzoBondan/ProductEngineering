export type SpringPage<T> = {
  content: T[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  numberOfElements?: number;
  empty: boolean;
};

export type BaseUsedMaterialDTO = {
  id: number;
  netQuantity: number;
  grossQuantity: number;
  measurementUnit: string;
}

export type BaseMaterialDTO = {
  code: number;
  description: string;
  family: string;
  implementation: Date;
  lostPercentage: number;
  color: ColorDTO;
}

export type BaseUsedAluminiumMaterialDTO = {
  id: number;
  quantity: number;
  measurementUnit: string;
  aluminiumSonCode: number;
}

export type AluminiumConfigurator = {
  items: AluminiumSonConfigurator[];
  colors: ColorDTO[];
  screws: ScrewConfigurator[];
};

export type AluminiumSonConfigurator = {
  fatherCode: number;
  aluminiumSonCode: number;
  description: string;
  sons: SonConfigurator[];
};

export type BPConfigurator = {
  items: BPConfiguratorSon[];
  colors: ColorDTO[];
};

export type BPConfiguratorSon = {
  fatherCode: number;
  description: string;
  sonCode: number;
  machinesIds: number[];
};

export type ModulationConfigurator = {
  items: ModulationSonConfigurator[];
  colors: ColorDTO[];
};

export type ModulationSonConfigurator = {
  fatherCode: number;
  fatherDescription: string;
  sons: SonConfigurator[];
  attachmentCodes: number[];
};

export type ScrewConfigurator = {
  code: number;
  quantity: number;
};

export type SonConfigurator = {
  code: number;
  description: string;
  edgeLength: number;
  edgeWidth: number;
  machinesIds: number[];
}

export type UserDTO = {
  id: number;
  name: string;
  email: string;
  imgUrl: string;
  roles: RoleDTO[];
};

export type RoleDTO = {
  id: number;
  authority: string;
};

export type ColorDTO = {
  code: number;
  name: string;
};

export type ItemDTO = {
  code: number;
  description: string;
  measure1: number;
  measure2: number;
  measure3: number;
};

export type AttachmentDTO = ItemDTO & {
};

export type MaterialDTO = {
  id: number;
  name: string;
};

export type FatherDTO = ItemDTO & {
  color: ColorDTO;
  ghost: GhostDTO;
  sons: SonDTO[];
  attachments: AttachmentDTO[];
};

export type SonDTO = ItemDTO & {
  fatherCode: number;
  color: ColorDTO;
  guide: GuideDTO;
}

export type MDPSonDTO = SonDTO & {
  sheets: SheetDTO[];
  edgeBandings: EdgeBandingDTO[];
  glues: GlueDTO[];
};

export type AluminiumSonDTO = SonDTO & {
  aluminiumType: AluminiumTypeDTO;
  sons: MDPSonDTO[];
  drawerPull: DrawerPullDTO;
  trySquares: UsedTrySquareDTO[];
  screws: UsedScrewDTO[];
  moldings: UsedMoldingDTO[];
  glass: GlassDTO;
};

export type AluminiumTypeDTO = {
  id: number;
  name: string;
  lessQuantity: number;
};

export type DrawerPullDTO = AttachmentDTO & {
};

export type GlassDTO = AttachmentDTO & {
  color: ColorDTO;
};

export type MoldingDTO = AttachmentDTO & {
};

export type ScrewDTO = AttachmentDTO & {
};

export type TrySquareDTO = AttachmentDTO & {
};

export type UsedDrawerPullDTO = BaseUsedAluminiumMaterialDTO & {
  drawerPullCode: number;
}

export type UsedGlassDTO = BaseUsedAluminiumMaterialDTO & {
  glassCode: number;
}

export type UsedMoldingDTO = BaseUsedAluminiumMaterialDTO & {
  moldingCode: number;
}

export type UsedScrewDTO = BaseUsedAluminiumMaterialDTO & {
  screwCode: number;
}

export type UsedTrySquareDTO = BaseUsedAluminiumMaterialDTO & {
  trySquareCode: number;
}

export type GuideDTO = {
  id: number;
  implementation: Date;
  finalDate: Date;
  guideMachines: GuideMachineDTO[];
};

export type GuideMachineDTO = {
  id: number;
  guideId: number;
  machineId: number;
  machineTime: number;
  manTime: number;
  measurementUnit: string;
};

export type MachineDTO = {
  id: number;
  name: string;
  formula: string[];
  machineGroupId: number;
  guideMachinesIds: number[];
};

export type MachineGroupDTO = {
  id: number;
  name: string;
  machinesIds: number[];
};

export type BackDTO = BaseMaterialDTO & {
  suffix: number;
  thickness: number;
  measure1: number;
  measure2: number;
  usedBackSheets: UsedBackSheetDTO[];
};

export type PaintingBorderBackgroundDTO = BaseMaterialDTO & {
};

export type PaintingDTO = BaseMaterialDTO & {
  paintingType: PaintingTypeDTO;
};

export type PaintingSonDTO = SonDTO & {
  satin: boolean;
  highShine: boolean;
  satinGlass: boolean;
  faces: number;
  special: boolean;
  suffix: number;
  back: BackDTO;
  paintings: UsedPaintingDTO[];
  paintingBorderBackgrounds: UsedPaintingBorderBackgroundDTO[];
  polyesters: UsedPolyesterDTO[];
};

export type PaintingTypeDTO = {
  id: number;
  description: string;
};

export type PolyesterDTO = BaseMaterialDTO & {
};

export type UsedBackSheetDTO = BaseUsedMaterialDTO & {
  sheetCode: number;
  backCode: number;
};

export type UsedPaintingBorderBackgroundDTO = BaseUsedMaterialDTO & {
  paintingBorderBackgroundCode: number;
  paintingSonCode: number;
};

export type UsedPaintingDTO = BaseUsedMaterialDTO & {
  paintingCode: number;
  paintingSonCode: number;
};

export type UsedPolyesterDTO = BaseUsedMaterialDTO & {
  polyesterCode: number;
  paintingSonCode: number;
};

export type EdgeBandingDTO = BaseMaterialDTO & {
  height: number;
  thickness: number;
};

export type GlueDTO = BaseMaterialDTO & {
  grammage: number;
};

export type SheetDTO = BaseMaterialDTO & {
  thickness: number;
  faces: number;
  materialId: number;
};

export type UsedEdgeBandingDTO = BaseUsedMaterialDTO & {
  edgeBandingCode: number;
  mdpSonCode: number;
};

export type UsedGlueDTO = BaseUsedMaterialDTO &{
  glueCode: number;
  mdpSonCode: number;
};

export type UsedSheetDTO = BaseUsedMaterialDTO & {
  sheetCode: number;
  mdpSonCode: number;
};

export type CornerBracketDTO = BaseMaterialDTO & {
};

export type GhostDTO = {
  code: number;
  suffix: string;
  description: string;
  measure1: number;
  measure2: number;
  measure3: number;
  cornerBrackets: UsedCornerBracketDTO[];
  nonwovenFabrics: UsedNonwovenFabricDTO[];
  plastics: UsedPlasticDTO[];
  polyethylenes: UsedPolyethyleneDTO[];
};

export type NonwovenFabricDTO = BaseMaterialDTO & {
};

export type PlasticDTO = BaseMaterialDTO & {
  grammage: number;
};

export type PolyethyleneDTO = BaseMaterialDTO & {
};

export type UsedCornerBracketDTO = BaseUsedMaterialDTO & {
  cornerBracketCode: number;
  ghostCode: string;
};

export type UsedNonwovenFabricDTO = BaseUsedMaterialDTO & {
  nonwovenFabricCode: number;
  ghostCode: string;
};

export type UsedPlasticDTO = BaseUsedMaterialDTO & {
  plasticCode: number;
  ghostCode: string;
};

export type UsedPolyethyleneDTO = BaseUsedMaterialDTO & {
  polyethyleneCode: number;
  ghostCode: string;
};