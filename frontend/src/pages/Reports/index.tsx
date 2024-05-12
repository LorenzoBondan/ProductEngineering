import React, { useState } from 'react';
import { TbReportAnalytics } from "react-icons/tb";
import './styles.css';
import DownloadButton from './DownloadButton';

const Reports = () => {
    const [id, setId] = useState<number>(0);
    const [pdfUrl, setPdfUrl] = useState<string | null>(null); 

    const handleDownload = (url: string) => {
      setPdfUrl(url); 
    };

    return (
        <div className="reports-container">
            <h1><TbReportAnalytics/> Relatório</h1>
            <form>
                <label>
                    Código do Pai
                    <input
                        type="number"
                        value={id}
                        onChange={(e) => setId(parseInt(e.target.value))}
                        className='base-input form-control'
                    />
                </label>
                <DownloadButton id={id} onDownload={handleDownload} />
            </form>
            {pdfUrl && ( // Se a URL do PDF estiver disponível, exibe um iframe com o PDF
                <iframe src={pdfUrl} style={{ width: '100%', height: '100%' }} title="Relatório PDF"></iframe>
            )}
        </div>
    );
}

export default Reports;
