import React from 'react';
import { saveAs } from 'file-saver';
import * as reportService from 'services/Reports/reportsService';

type Props = {
    id: number;
    onDownload: (url: string) => void; // Função para ser chamada quando o PDF for baixado
}

const DownloadButton = ({ id, onDownload } : Props) => {
    const downloadPdf = async (event: React.MouseEvent) => {
      event.preventDefault();

      try {
        const response = await reportService.report(id);
  
        const pdfBlob = new Blob([response.data], { type: 'application/pdf' });
  
        const pdfUrl = URL.createObjectURL(pdfBlob);
        saveAs(pdfBlob, `report ${id}.pdf`);
        onDownload(pdfUrl);
      } catch (error) {
        console.error('Erro ao baixar o arquivo PDF', error);
      }
    };
  
    return <button onClick={downloadPdf} className='btn btn-primary text-white'>Baixar PDF</button>;
};

export default DownloadButton;
