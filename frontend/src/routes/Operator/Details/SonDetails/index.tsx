import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import * as filhoService from "../../../../services/filhoService";
import * as acessorioUsadoService from "../../../../services/acessorioUsadoService";
import * as materialUsadoService from "../../../../services/materialUsadoService";
import { getLabel } from "../../../../models/enums/tipoFilho";
import { DFilho } from "../../../../models/filho";
import { formatDate } from "../../../../utils/formatters";
import DialogConfirmation from "../../../../components/DialogConfirmation";
import DialogInfo from "../../../../components/DialogInfo";
import { Link } from "react-router-dom";
import eyeIcon from '../../../../assets/images/eye.svg';
import editIcon from '../../../../assets/images/edit.svg';
import deleteIcon from '../../../../assets/images/delete.svg';

export default function SonDetails() {

  const params = useParams();
  const navigate = useNavigate();
  const [filho, setFilho] = useState<DFilho>();

  useEffect(() => {
    findSonById();
  }, [params.sonId]);

  const findSonById = () => {
    const sonId = Number(params.sonId);
    if (!isNaN(sonId)) {
      filhoService
        .pesquisarPorId(sonId)
        .then((response) => {
          setFilho(response.data);
        })
        .catch(() => {
          navigate("/sons");
        });
    } else {
      navigate("/sons");
    }
  };

  const [dialogInfoData, setDialogInfoData] = useState({
    visible: false,
    message: "Sucesso!"
  });

  const [dialogConfirmationData, setDialogConfirmationData] = useState({
    visible: false,
    id: 0,
    message: "Você tem certeza?",
    type: ""
  });

  function handleDialogInfoClose() {
    setDialogInfoData({ ...dialogInfoData, visible: false });
  }

  function handleDialogConfirmationAnswer(answer: boolean, id: number[]) {
    if (answer) {
      if (dialogConfirmationData.type === "accessory") {
        acessorioUsadoService.remover(id)
          .then(() => findSonById())
          .catch(error => {
            setDialogInfoData({
              visible: true,
              message: error.response.data.error
            });
          });
      } else if (dialogConfirmationData.type === "material") {
        materialUsadoService.remover(id)
          .then(() => findSonById())
          .catch(error => {
            setDialogInfoData({
              visible: true,
              message: error.response.data.error
            });
          });
      } else if (dialogConfirmationData.type === "son") {
        filhoService.remover(id)
          .then(() => findSonById())
          .catch(error => {
            setDialogInfoData({
              visible: true,
              message: error.response.data.error
            });
          });
      }
    }
    setDialogConfirmationData({ ...dialogConfirmationData, visible: false });
  } 

  // lista de acessorios usados

  function handleAccessoryUpdateClick(usedAccessoryId: number) {
    navigate(`/usedAccessories/${usedAccessoryId}`);
  }

  function handleAccessoryDeleteClick(usedAccessoryId: number) {
    setDialogConfirmationData({ ...dialogConfirmationData, id: usedAccessoryId, visible: true, type: "accessory" });
  }

  // lista de materiais usados

  function handleMaterialUpdateClick(usedMaterialId: number) {
    navigate(`/usedMaterials/${usedMaterialId}`);
  }

  function handleMaterialDeleteClick(usedMaterialId: number) {
    setDialogConfirmationData({ ...dialogConfirmationData, id: usedMaterialId, visible: true, type: "material" });
  }

  // lista de filhos

  function handleSonUpdateClick(sonId: number) {
    navigate(`/sons/${sonId}`);
  }

  function handleSonDeleteClick(sonId: number) {
    setDialogConfirmationData({ ...dialogConfirmationData, id: sonId, visible: true, type: "son" });
  }

  return (
    <main className="father-details-main">
      <section className="container">
        <div className="card father-card">
          <div className="father-header">
            <h1 className="father-title">
              {filho?.codigo} - {filho?.descricao}
            </h1>
          </div>
          <div className="father-info">
            <div className="info-item">
              <strong>Código:</strong> {filho?.codigo}
            </div>
            <div className="info-item">
              <strong>Descrição:</strong> {filho?.descricao}
            </div>
            <div className="info-item">
              <strong>Pai:</strong> {filho?.pai ? <p onClick={() => (window.location.href = `/fathers/details/${filho?.pai.codigo}`)}
                  style={{ cursor: "pointer" }}>{filho?.pai.descricao}</p> : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Cor:</strong> {filho?.cor ? filho?.cor?.descricao : "Não especificada"}
            </div>
            <div className="info-item">
              <strong>Altura:</strong> {filho?.medidas ? filho?.medidas.altura : "Não especificada"} mm
            </div>
            <div className="info-item">
              <strong>Largura:</strong> {filho?.medidas ? filho?.medidas.largura : "Não especificada"} mm
            </div>
            <div className="info-item">
              <strong>Espessura:</strong> {filho?.medidas ? filho?.medidas.espessura : "Não especificada"} mm
            </div>
            <div className="info-item">
              <strong>Roteiro:</strong> {filho?.roteiro ? <p onClick={() => (window.location.href = `/guides/details/${filho?.roteiro.codigo}`)}
                  style={{ cursor: "pointer" }}>{filho?.roteiro.descricao}</p> : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Implantação:</strong> {filho?.implantacao ? formatDate(filho.implantacao.toString()) : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Tipo:</strong> {filho?.tipo ? getLabel(filho.tipo) : "Não Especificado"}
            </div>
            <div className="info-item">
              <strong>Valor:</strong> R$ {filho?.valor}
            </div>
          </div>
        </div>
        
        <div className="sons-section">
          <h2>Materiais</h2>
          <table className="table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Descrição</th>
                <th>Quantidade Líquida</th>
                <th>Quantidade Bruta</th>
                <th>Valor (R$)</th>
                <th></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filho?.materiaisUsados.filter(obj => obj.situacao === 'ATIVO')
              .map((materialUsado) => (
                <tr>
                  <td>{materialUsado.material.codigo}</td>
                  <td>{materialUsado.material.descricao}</td>
                  <td>{materialUsado.quantidadeLiquida} {materialUsado.unidadeMedida}</td>
                  <td>{materialUsado.quantidadeBruta} {materialUsado.unidadeMedida}</td>
                  <td>{materialUsado.valor}</td>
                  <td><img onClick={() => handleMaterialUpdateClick(materialUsado.codigo)} className="edit-btn" src={editIcon} alt="Editar" /></td>
                  <td><img onClick={() => handleMaterialDeleteClick(materialUsado.codigo)} className="delete-btn" src={deleteIcon} alt="Deletar" /></td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="sons-section">
          <h2>Acessórios</h2>
          <table className="table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Descrição</th>
                <th>Quantidade</th>
                <th>Valor (R$)</th>
                <th></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filho?.acessoriosUsados.map((acessorioUsado) => (
                <tr>
                  <td>{acessorioUsado.acessorio.codigo}</td>
                  <td>{acessorioUsado.acessorio.descricao}</td>
                  <td>{acessorioUsado.quantidade} {acessorioUsado.unidadeMedida}</td>
                  <td>{acessorioUsado.valor}</td>
                  <td><img onClick={() => handleAccessoryUpdateClick(acessorioUsado.codigo)} className="edit-btn" src={editIcon} alt="Editar" /></td>
                  <td><img onClick={() => handleAccessoryDeleteClick(acessorioUsado.codigo)} className="delete-btn" src={deleteIcon} alt="Deletar" /></td>
                </tr>
              ))}
            </tbody>
          </table>
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
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filho?.filhos.map((filho) => (
                <tr>
                  <td>{filho.codigo}</td>
                  <td>{filho.descricao}</td>
                  <td>{filho.cor.descricao}</td>
                  <td>
                    {filho.medidas.altura} x {filho.medidas.largura} x {filho.medidas.espessura}
                  </td>
                  <td><Link to={`/sons/details/${filho.codigo}`}><img className="visualize-btn" src={eyeIcon} alt="" /></Link></td>
                  <td><img onClick={() => handleSonUpdateClick(filho.codigo)} className="edit-btn" src={editIcon} alt="Editar" /></td>
                  <td><img onClick={() => handleSonDeleteClick(filho.codigo)} className="delete-btn" src={deleteIcon} alt="Deletar" /></td>
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