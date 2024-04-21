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

export type DColor = {
  code: number;
  name: string;
};

export type DBaseUsedMaterial = {
  id: number;
  netQuantity: number;
  grossQuantity: number;
  measurementUnit: string;
}

export type DBaseMaterial = {
  code: number;
  description: string;
  family: string;
  implementation: Date;
  lostPercentage: number;
  color: DColor;
}

export type DBaseUsedAluminiumMaterial = {
  id: number;
  quantity: number;
  measurementUnit: string;
  aluminiumSonCode: number;
}

export type AluminiumConfigurator = {
  items: AluminiumSonConfigurator[];
  colors: DColor[];
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
  colors: DColor[];
};

export type BPConfiguratorSon = {
  fatherCode: number;
  description: string;
  sonCode: number;
  machinesIds: number[];
};

export type ModulationConfigurator = {
  items: ModulationSonConfigurator[];
  colors: DColor[];
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

export type DUser = {
  id: number;
  name: string;
  password: string;
  email: string;
  imgUrl: string;
  roles: DRole[];
};

export type DRole = {
  id: number;
  authority: string;
};

export type DItem = {
  code: number;
  description: string;
  measure1: number;
  measure2: number;
  measure3: number;
  measurementUnit: string;
  implementation: Date;
};

export type DAttachment = DItem & {
};

export type DMaterial = {
  id: number;
  name: string;
};

export type DFather = DItem & {
  color: DColor;
  ghost: DGhost;
  sons: DSon[];
  attachments: DAttachment[];
};

export type DSon = DItem & {
  fatherCode: number;
  color: DColor;
  guide: DGuide;
}

export type DMDPSon = DSon & {
  sheets: DSheet[];
  edgeBandings: DEdgeBanding[];
  glues: DGlue[];
};

export type DAluminiumSon = DSon & {
  aluminiumType: DAluminiumType;
  sons: DMDPSon[];
  drawerPull: DDrawerPull;
  trySquares: DUsedTrySquare[];
  screws: DUsedScrew[];
  moldings: DUsedMolding[];
  glass: DGlass;
};

export type DAluminiumType = {
  id: number;
  name: string;
  lessQuantity: number;
};

export type DDrawerPull = DAttachment & {
};

export type DGlass = DAttachment & {
  color: DColor;
};

export type DMolding = DAttachment & {
};

export type DScrew = DAttachment & {
};

export type DTrySquare = DAttachment & {
};

export type DUsedDrawerPull = DBaseUsedAluminiumMaterial & {
  drawerPullCode: number;
}

export type DUsedGlass = DBaseUsedAluminiumMaterial & {
  glassCode: number;
}

export type DUsedMolding = DBaseUsedAluminiumMaterial & {
  moldingCode: number;
}

export type DUsedScrew = DBaseUsedAluminiumMaterial & {
  screwCode: number;
}

export type DUsedTrySquare = DBaseUsedAluminiumMaterial & {
  trySquareCode: number;
}

export type DGuide = {
  id: number;
  implementation: Date;
  finalDate: Date;
  guideMachines: DGuideMachine[];
};

export type DGuideMachine = {
  id: number;
  guideId: number;
  machineId: number;
  machineTime: number;
  manTime: number;
  measurementUnit: string;
};

export type DMachine = {
  id: number;
  name: string;
  formula: string[];
  machineGroupId: number;
  guideMachinesIds: number[];
};

export type DMachineGroup = {
  id: number;
  name: string;
  machinesIds: number[];
};

export type DBack = DBaseMaterial & {
  suffix: number;
  thickness: number;
  measure1: number;
  measure2: number;
  usedBackSheets: DUsedBackSheet[];
};

export type DPaintingBorderBackground = DBaseMaterial & {
};

export type DPainting = DBaseMaterial & {
  paintingType: DPaintingType;
};

export type DPaintingSon = DSon & {
  satin: boolean;
  highShine: boolean;
  satinGlass: boolean;
  faces: number;
  special: boolean;
  suffix: number;
  back: DBack;
  paintings: DUsedPainting[];
  paintingBorderBackgrounds: DUsedPaintingBorderBackground[];
  polyesters: DUsedPolyester[];
};

export type DPaintingType = {
  id: number;
  description: string;
};

export type DPolyester = DBaseMaterial & {
};

export type DUsedBackSheet = DBaseUsedMaterial & {
  sheetCode: number;
  backCode: number;
};

export type DUsedPaintingBorderBackground = DBaseUsedMaterial & {
  paintingBorderBackgroundCode: number;
  paintingSonCode: number;
};

export type DUsedPainting = DBaseUsedMaterial & {
  paintingCode: number;
  paintingSonCode: number;
};

export type DUsedPolyester = DBaseUsedMaterial & {
  polyesterCode: number;
  paintingSonCode: number;
};

export type DEdgeBanding = DBaseMaterial & {
  height: number;
  thickness: number;
};

export type DGlue = DBaseMaterial & {
  grammage: number;
};

export type DSheet = DBaseMaterial & {
  thickness: number;
  faces: number;
  materialId: number;
};

export type DUsedEdgeBanding = DBaseUsedMaterial & {
  edgeBandingCode: number;
  mdpSonCode: number;
};

export type DUsedGlue = DBaseUsedMaterial &{
  glueCode: number;
  mdpSonCode: number;
};

export type DUsedSheet = DBaseUsedMaterial & {
  sheetCode: number;
  mdpSonCode: number;
};

export type DCornerBracket = DBaseMaterial & {
};

export type DGhost = {
  code: number;
  suffix: string;
  description: string;
  measure1: number;
  measure2: number;
  measure3: number;
  cornerBrackets: DUsedCornerBracket[];
  nonwovenFabrics: DUsedNonwovenFabric[];
  plastics: DUsedPlastic[];
  polyethylenes: DUsedPolyethylene[];
};

export type DNonwovenFabric = DBaseMaterial & {
};

export type DPlastic = DBaseMaterial & {
  grammage: number;
};

export type DPolyethylene = DBaseMaterial & {
};

export type DUsedCornerBracket = DBaseUsedMaterial & {
  cornerBracketCode: number;
  ghostCode: string;
};

export type DUsedNonwovenFabric = DBaseUsedMaterial & {
  nonwovenFabricCode: number;
  ghostCode: string;
};

export type DUsedPlastic = DBaseUsedMaterial & {
  plasticCode: number;
  ghostCode: string;
};

export type DUsedPolyethylene = DBaseUsedMaterial & {
  polyethyleneCode: number;
  ghostCode: string;
};

/* DTOS */

export type SheetDTO = {
  code: number;
  description: string;
  family: string;
  implementation: Date;
  lostPercentage: number;
  color: DColor;
  thickness: number;
  faces: number;
  material: DMaterial;
}