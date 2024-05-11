import { DAluminiumSon, DMDPSon, DPaintingSon } from "models/entities";
import { useCallback, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import * as mdpSonService from 'services/MDP/mdpSonService';
import * as paintingSonService from 'services/MDF/paintingSonService';
import * as aluminiumSonService from 'services/Aluminium/aluminiumSonService';
import checkedImg from 'assets/images/checked.png';
import uncheckedImg from 'assets/images/unchecked.png';
import './styles.css';
import UsedSheetModal from "./MDPMaterials/Sheets/UsedSheetModal";
import { IoMdAdd } from "react-icons/io";
import UsedSheetRow from "./MDPMaterials/Sheets/UsedSheetRow";
import UsedEdgeBandingRow from "./MDPMaterials/EdgeBandings/UsedEdgeBandingRow";
import UsedEdgeBandingModal from "./MDPMaterials/EdgeBandings/UsedEdgeBandingModal";
import UsedGlueRow from "./MDPMaterials/Glues/UsedGlueRow";
import UsedGlueModal from "./MDPMaterials/Glues/UsedGlueModal";
import UsedPaintingRow from "./MDFMaterials/Paintings/UsedPaintingRow";
import UsedPaintingModal from "./MDFMaterials/Paintings/UsedPaintingModal";
import UsedPaintingBorderBackgroundRow from "./MDFMaterials/PaintingBorderBackgrounds/UsedPaintingBorderBackgroundRow";
import UsedPaintingBorderBackgroundModal from "./MDFMaterials/PaintingBorderBackgrounds/UsedPaintingBorderBackgroundModal";
import UsedPolyesterRow from "./MDFMaterials/Polyesters/UsedPolyestersRow";
import UsedPolyesterModal from "./MDFMaterials/Polyesters/UsedPolyesterModal";

type UrlParams = {
    sonId: string;
}

const SonDetails = () => {

    const { sonId } = useParams<UrlParams>();
    const [ mdpSon, setMdpSon] = useState<DMDPSon>();
    const [ paintingSon, setPaintingSon] = useState<DPaintingSon>();
    const [ aluminiumSon, setAluminiumSon] = useState<DAluminiumSon>();
    const [ isMdpSon, setIsMdpSon] = useState<boolean>(false);
    const [ isPaintingSon, setIsPaintingSon] = useState<boolean>(false);
    const [ isAluminiumSon, setIsAluminiumSon] = useState<boolean>(false);

    const getSon = useCallback(() => {
        mdpSonService.findById(parseInt(sonId))
            .then(response => {
                setMdpSon(response.data);
                setIsMdpSon(true);
            })
            .catch(() => {
                paintingSonService.findById(parseInt(sonId))
                    .then(response => {
                        setPaintingSon(response.data);
                        setIsPaintingSon(true);
                    })
                    .catch(() => {
                        aluminiumSonService.findById(parseInt(sonId))
                        .then(response => {
                            setAluminiumSon(response.data);
                            setIsAluminiumSon(true);
                        })
                        .catch(error => console.log("Error: " + error));
                    });
            });
    }, [sonId]);

    useEffect(() => {
        getSon();
    }, [getSon]);

    // Estados dos modais
    const [sheetModalIsOpen, setSheetModalIsOpen] = useState(false);
    const [edgeBandingModalIsOpen, setEdgeBandingModalIsOpen] = useState(false);
    const [glueModalIsOpen, setGlueModalIsOpen] = useState(false);

    const [paintingModalIsOpen, setPaintingModalIsOpen] = useState(false);
    const [paintingBorderBackgroundModalIsOpen, setPaintingBorderBackgroundModalIsOpen] = useState(false);
    const [polyesterModalIsOpen, setPolyesterModalIsOpen] = useState(false);

    // Funções para abrir os modais específicos
    const openSheetModal = () => {
        setSheetModalIsOpen(true);
    }

    const openEdgeBandingModal = () => {
        setEdgeBandingModalIsOpen(true);
    }

    const openGlueModal = () => {
        setGlueModalIsOpen(true);
    }

    const openPaintingModal = () => {
        setPaintingModalIsOpen(true);
    }

    const openPaintingBorderBackgroundModal = () => {
        setPaintingBorderBackgroundModalIsOpen(true);
    }

    const openPolyesterModal = () => {
        setPolyesterModalIsOpen(true);
    }

    // Funções para fechar os modais
    const closeModal = () => {
        setSheetModalIsOpen(false);
        setEdgeBandingModalIsOpen(false);
        setGlueModalIsOpen(false);
        setPaintingModalIsOpen(false);
        setPaintingBorderBackgroundModalIsOpen(false);
        setPolyesterModalIsOpen(false);
    }

    return(
        <div className="father-details-page">
            <div className='father-details-container base-card'>
                { /* MDP */}
                {isMdpSon && 
                    <>
                    <div className='father-title crud-modal-title'>
                        <h4>{mdpSon?.code} - {mdpSon?.description}</h4>
                    </div>
                    <div className="father-details">
                        <span>Pai: {mdpSon?.fatherCode}</span>
                    </div>
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Chapas</h5>
                            <IoMdAdd onClick={openSheetModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {mdpSon?.sheets.map(sheet => 
                                <>
                                    <UsedSheetRow usedSheet={sheet} onDeleteOrEdit={getSon} key={sheet.id} />
                                    <UsedSheetModal usedSheet={sheet} mdpSonCode={mdpSon.code} isOpen={sheetModalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            )}
                        </ul>
                    </div>
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Fitas Borda</h5>
                            <IoMdAdd onClick={openEdgeBandingModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {mdpSon?.edgeBandings.map(edgeBanding => (
                                <>
                                    <UsedEdgeBandingRow usedEdgeBanding={edgeBanding} onDeleteOrEdit={getSon} key={edgeBanding.id} />
                                    <UsedEdgeBandingModal usedEdgeBanding={edgeBanding} mdpSonCode={mdpSon.code} isOpen={edgeBandingModalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            ))}
                        </ul>
                    </div>
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Colas</h5>
                            <IoMdAdd onClick={openGlueModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {mdpSon?.glues.map(glue => (
                                <>
                                    <UsedGlueRow usedGlue={glue} onDeleteOrEdit={getSon} key={glue.id} />
                                    <UsedGlueModal usedGlue={glue} mdpSonCode={mdpSon.code} isOpen={glueModalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            ))}
                        </ul>
                    </div>
                    { /* Roteiro */}
                    </>
                }

                { /* MDF */}
                {isPaintingSon && 
                <>
                    <div className='father-title crud-modal-title'>
                        <h4>{paintingSon?.code} - {paintingSon?.description}</h4>
                    </div>
                    <div className="father-details">
                        <span>Pai: {paintingSon?.fatherCode}</span>
                        {
                            paintingSon?.satin ? "Acetinada" :
                            paintingSon?.highShine ? "Alto Brilho" :
                            paintingSon?.satinGlass ? "Acetinada vidros" :
                            ""
                        }
                        <span>Faces: {paintingSon?.faces}</span>
                        <span className="special-span">Especial: {paintingSon?.special ? <img src={checkedImg} alt="" /> : <img src={uncheckedImg} alt="" />}</span>
                    </div>

                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Fundo</h5>
                        </div>
                        <ul className='father-sons-list'>
                            <Link to={`/backs/${paintingSon?.back.code}`}>
                                <li className='son-list-item'>
                                    {paintingSon?.back.code} - {paintingSon?.back.description}
                                </li>
                            </Link>
                        </ul>
                    </div>

                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Pinturas</h5>
                            <IoMdAdd onClick={openPaintingModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {paintingSon?.paintings.map(painting => (
                                <>
                                    <UsedPaintingRow usedPainting={painting} onDeleteOrEdit={getSon} key={painting.id} />
                                    <UsedPaintingModal usedPainting={painting} paintingSonCode={paintingSon.code} isOpen={paintingModalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            ))}
                        </ul>
                    </div>
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Pinturas de Borda de Fundo</h5>
                            <IoMdAdd onClick={openPaintingBorderBackgroundModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {paintingSon?.paintingBorderBackgrounds.map(paintingBorderBackground => (
                                <>
                                    <UsedPaintingBorderBackgroundRow usedPaintingBorderBackground={paintingBorderBackground} onDeleteOrEdit={getSon} key={paintingBorderBackground.id} />
                                    <UsedPaintingBorderBackgroundModal usedPaintingBorderBackground={paintingBorderBackground} paintingSonCode={paintingSon.code} isOpen={paintingBorderBackgroundModalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            ))}
                        </ul>
                    </div>
                    <div className="son-material-container">
                        <div className="son-material-top-menu">
                            <h5>Poliésters</h5>
                            <IoMdAdd onClick={openPolyesterModal} /> 
                        </div>
                        <ul className='father-sons-list'>
                            {paintingSon?.polyesters.map(polyester => (
                                <>
                                    <UsedPolyesterRow usedPolyester={polyester} onDeleteOrEdit={getSon} key={polyester.id} />
                                    <UsedPolyesterModal usedPolyester={polyester} paintingSonCode={paintingSon.code} isOpen={polyesterModalIsOpen} isEditing={false} onClose={closeModal} onDeleteOrEdit={() => getSon()} />
                                </>
                            ))}
                        </ul>
                    </div>
                    { /* Roteiro */}
                </>
                }

                { /* Aluminium Son */}
                
            </div>
        </div>
    );
}

export default SonDetails;