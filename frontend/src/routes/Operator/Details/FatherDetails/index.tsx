import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { DPai } from "../../../../models/pai";
import * as paiService from "../../../../services/paiService";
import * as filhoService from "../../../../services/filhoService";
import { getLabel } from "../../../../models/enums/tipoPintura";
import { FaCheckCircle, FaTimesCircle } from "react-icons/fa";
import "./styles.css";
import { Link } from "react-router-dom";
import DialogConfirmation from "../../../../components/DialogConfirmation";
import DialogInfo from "../../../../components/DialogInfo";
import eyeIcon from '../../../../assets/images/eye.svg';
import DropdownMenu from "../../../../components/DropdownMenu";

export default function FatherDetails() {
  const params = useParams();
  const navigate = useNavigate();
  const [pai, setPai] = useState<DPai>();

  useEffect(() => {
    findFatherById();
  }, [params.fatherId]);

  const findFatherById = () => {
    const fatherId = Number(params.fatherId);
    if (!isNaN(fatherId)) {
      paiService
        .pesquisarPorId(fatherId)
        .then((response) => {
          setPai(response.data);
        })
        .catch(() => {
          navigate("/fathers");
        });
    } else {
      navigate("/fathers");
    }
  };

  // lista de filhos

  const [dialogInfoData, setDialogInfoData] = useState({
    visible: false,
    message: "Sucesso!"
  });

  const [dialogConfirmationData, setDialogConfirmationData] = useState({
    visible: false,
    id: 0,
    message: "Você tem certeza?"
  });

  function handleDialogInfoClose() {
    setDialogInfoData({ ...dialogInfoData, visible: false });
  }

  function handleUpdateClick(sonId: number) {
    navigate(`/sons/${sonId}`);
  }

  function handleDeleteClick(sonId: number) {
    setDialogConfirmationData({ ...dialogConfirmationData, id: sonId, visible: true });
  }

  function handleDialogConfirmationAnswer(answer: boolean, sonId: number[]) {
    if (answer) {
      filhoService.remover(sonId)
        .then(() => {
          findFatherById();
        })
        .catch(error => {
          setDialogInfoData({
            visible: true,
            message: error.response.data.error
        })
      });
      }
    setDialogConfirmationData({ ...dialogConfirmationData, visible: false });
  }

  function handleInactivate(id: number[]) {
    filhoService.inativar(id)
    .then(() => findFatherById())
    .catch(error => {
      setDialogInfoData({
        visible: true,
        message: error.response.data.error
      });
    });
  }

  return (
    <main className="father-details-main">
      <section className="container father-container">
        <div className="card father-card">
          <div className="father-header">
            <h1 className="father-title">
              {pai?.codigo} - {pai?.descricao}
            </h1>
          </div>
          <div className="father-info">
            <div className="info-item">
              <strong>Código:</strong> {pai?.codigo}
            </div>
            <div className="info-item">
              <strong>Descrição:</strong> {pai?.descricao}
            </div>
            <div className="info-item">
              <strong>Modelo:</strong> {pai?.modelo?.descricao || "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Categoria Componente:</strong> {pai?.categoriaComponente?.descricao || "Não especificada"}
            </div>
            <div className="info-item">
              <strong>Bordas - Comprimento:</strong> {pai?.bordasComprimento || "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Bordas - Largura:</strong> {pai?.bordasLargura || "Não especificado"}
            </div>
            <div className="info-item">
              <strong>TNT Uma Face:</strong> {pai?.tntUmaFace ? <FaCheckCircle className="icon success" /> : <FaTimesCircle className="icon error" />}
            </div>
            <div className="info-item">
              <strong>Plástico Acima:</strong> {pai?.plasticoAcima ? <FaCheckCircle className="icon success" /> : <FaTimesCircle className="icon error" />}
            </div>
            <div className="info-item">
              <strong>Tipo:</strong> {pai?.tipoPintura ? getLabel(pai.tipoPintura) : "Não Especificado"}
            </div>
          </div>
        </div>
        <div className="sons-section">
          <h2>Filhos</h2>
          <table className="table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Descrição</th>
                <th>Cor</th>
                <th>Medidas</th>
                <th></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {pai?.filhos.filter(obj => obj.situacao !== 'LIXEIRA')
              .map((filho) => (
                <tr key={filho.codigo} className={`situacao-${filho.situacao.toLowerCase()}`}>
                  <td>{filho.codigo}</td>
                  <td>{filho.descricao}</td>
                  <td>{filho.cor.descricao}</td>
                  <td>
                    {filho.medidas.altura} x {filho.medidas.largura} x {filho.medidas.espessura}
                  </td>
                  <td><Link to={`/sons/details/${filho.codigo}`}><img className="visualize-btn" src={eyeIcon} alt="" /></Link></td>
                  <td>
                    <DropdownMenu
                      onEdit={() => handleUpdateClick(filho.codigo)}
                      onInactivate={() => handleInactivate([filho.codigo])}
                      onDelete={() => handleDeleteClick(filho.codigo)}
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>

      {
        dialogInfoData.visible &&
          <DialogInfo
            message={dialogInfoData.message}
            onDialogClose={handleDialogInfoClose}
          />
      }

      {
        dialogConfirmationData.visible &&
          <DialogConfirmation
            id={dialogConfirmationData.id}
            message={dialogConfirmationData.message}
            onDialogAnswer={handleDialogConfirmationAnswer}
          />
      }
    </main>
  );
}