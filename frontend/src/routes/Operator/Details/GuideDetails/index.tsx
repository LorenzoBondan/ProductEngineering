import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import * as roteiroService from "../../../../services/roteiroService";
import * as roteiroMaquinaService from "../../../../services/roteiroMaquinaService";
import { DRoteiro } from "../../../../models/roteiro";
import { formatDate } from "../../../../utils/formatters";
import DropdownMenu from "../../../../components/DropdownMenu";
import DialogConfirmation from "../../../../components/DialogConfirmation";
import DialogInfo from "../../../../components/DialogInfo";

export default function GuideDetails() {

  const params = useParams();
  const navigate = useNavigate();
  const [roteiro, setRoteiro] = useState<DRoteiro>();

  useEffect(() => {
    findGuideById();
  }, [params.guideId]);

  const findGuideById = () => {
    const guideId = Number(params.guideId);
    if (!isNaN(guideId)) {
      roteiroService
        .pesquisarPorId(guideId)
        .then((response) => {
          setRoteiro(response.data);
        })
        .catch(() => {
          navigate("/guides");
        });
    } else {
      navigate("/guides");
    }
  };

  // lista de máquinas

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

  function handleUpdateClick(guideMachineId: number) {
    navigate(`/guideMachines/${guideMachineId}`);
  }

  function handleDeleteClick(guideMachineId: number) {
    setDialogConfirmationData({ ...dialogConfirmationData, id: guideMachineId, visible: true });
  }

  function handleDialogConfirmationAnswer(answer: boolean, guideMachineId: number[]) {
    if (answer) {
      roteiroMaquinaService.remover(guideMachineId)
        .then(() => {
          findGuideById();
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
    roteiroMaquinaService.inativar(id)
    .then(() => findGuideById())
    .catch(error => {
      setDialogInfoData({
        visible: true,
        message: error.response.data.error
      });
    });
  }

  return (
    <main className="father-details-main">
      <section className="container">
        <div className="card father-card">
          <div className="father-header">
            <h1 className="father-title">
              {roteiro?.codigo} - {roteiro?.descricao}
            </h1>
          </div>
          <div className="father-info">
            <div className="info-item">
              <strong>Código:</strong> {roteiro?.codigo}
            </div>
            <div className="info-item">
              <strong>Descrição:</strong> {roteiro?.descricao}
            </div>
            <div className="info-item">
              <strong>Implantação:</strong> {roteiro?.implantacao ? formatDate(roteiro?.implantacao.toString()) : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Data final:</strong> {roteiro?.dataFinal ? formatDate(roteiro?.dataFinal.toString()) : "Não especificado"}
            </div>
            <div className="info-item">
              <strong>Valor:</strong> {roteiro?.valor || "Não especificado"}
            </div>
          </div>
        </div>
        <div className="sons-section">
          <h2>Máquinas</h2>
          <table className="table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Tempo</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {roteiro?.roteiroMaquinas.filter(obj => obj.situacao !== 'LIXEIRA')
              .map((roteiroMaquina) => (
                <tr key={roteiroMaquina.codigo} className={`situacao-${roteiroMaquina.situacao.toLowerCase()}`}>
                  <td>{roteiroMaquina.maquina.codigo}</td>
                  <td>{roteiroMaquina.maquina.nome}</td>
                  <td>{roteiroMaquina.tempoMaquina}</td>
                  <td>
                    <DropdownMenu
                      onEdit={() => handleUpdateClick(roteiroMaquina.codigo)}
                      onInactivate={() => handleInactivate([roteiroMaquina.codigo])}
                      onDelete={() => handleDeleteClick(roteiroMaquina.codigo)}
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