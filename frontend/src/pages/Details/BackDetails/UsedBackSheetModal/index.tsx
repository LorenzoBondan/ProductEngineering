import React, { ReactNode, useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import * as usedBackSheetService from 'services/MDF/usedBackSheetService';
import * as sheetService from 'services/MDP/sheetService';
import { DUsedBackSheet, DSheet } from 'models/entities';
import {ReactComponent as EditSvg} from "assets/images/edit.svg";
import {ReactComponent as AddSvg} from "assets/images/add.svg";

type UsedBackSheetModalProps = {
    usedBackSheet?: DUsedBackSheet;
    isOpen: boolean;
    isEditing: boolean;
    onClose: () => void;
    onDeleteOrEdit: () => void;
    children?: ReactNode;
    backCode : number;
}

const UsedBackSheetModal: React.FC<UsedBackSheetModalProps> = ({ usedBackSheet, isOpen, isEditing, onClose, onDeleteOrEdit, backCode }) => {

  const [errorMessage, setErrorMessage] = useState<string>('');
  const { register, handleSubmit, formState: { errors }, setValue } = useForm<DUsedBackSheet>();
  const [selectSheets, setSelectSheets] = useState<DSheet[]>();

  // load inputs

  useEffect(() => {
    if(isEditing && usedBackSheet){
        usedBackSheetService.findById(usedBackSheet.id)
        .then((response) => {
            const fetchedUsedBackSheet = response.data as DUsedBackSheet;

            setValue('id', fetchedUsedBackSheet.id);
            setValue('backCode', backCode);
            setValue('sheetCode', fetchedUsedBackSheet.sheetCode);
            setValue('grossQuantity', fetchedUsedBackSheet.grossQuantity);
            setValue('netQuantity', fetchedUsedBackSheet.netQuantity);
            setValue('measurementUnit', fetchedUsedBackSheet.measurementUnit);
        });
    } else if (!isEditing && usedBackSheet){
        setValue('backCode', backCode);
    }
  }, [isEditing, usedBackSheet, setValue, backCode]);

    // populate comboboxes

    useEffect(() => {

        if(usedBackSheet?.sheetCode){
            sheetService.findAllActiveAndCurrentOne(usedBackSheet?.sheetCode)
                .then(response => setSelectSheets(response.data));
        } else{
            sheetService.findAll('')
                .then(response => setSelectSheets(response.data.content));
        }
    
    }, [usedBackSheet?.sheetCode]);

  // insert / update method

  const insertOrUpdate = (formData: DUsedBackSheet) => {

    const serviceFunction = isEditing ? usedBackSheetService.update : usedBackSheetService.insert;
    const successMessage = isEditing ? 'Chapa utilizada editada!' : 'Chapa utilizada inserida!';
  
    serviceFunction(formData)
      .then(response => {
        console.log(response.data);
        setErrorMessage('');
        toast.success(successMessage);
        onClose();
        onDeleteOrEdit();
      })
      .catch(error => {
        toast.error(error.response.data.error);
        setErrorMessage(error.response.data.error);
      });
  };

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onClose}
      contentLabel="Example Modal"
      overlayClassName="modal-overlay"
      className="modal-content"
    >
      <form onSubmit={handleSubmit(insertOrUpdate)} className="crud-modal-form">
        {isEditing ? <div className='crud-modal-title'><EditSvg/><h2>Editar</h2></div> : <div className='crud-modal-title'><AddSvg/><h2>Adicionar</h2></div>}
        <div className="row crud-modal-inputs-container">
            <div className="col-lg-6 crud-modal-half-container">
                <div className='margin-bottom-10'>
                    <label htmlFor="">Código do Fundo</label>
                    <input 
                        {...register("backCode", {
                        required: 'Campo obrigatório', minLength: 8
                        })}
                        type="number"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.backCode ? 'is-invalid' : ''}`}
                        placeholder="Código do Fundo"
                        name="backCode"
                        disabled
                    />
                    <div className='invalid-feedback d-block'>{errors.backCode?.message}</div>
                </div> 
                <div className='margin-bottom-10'>
                    <label htmlFor="">Chapa</label> 
                    <select
                        {...register("sheetCode", {
                            required: 'Campo obrigatório',
                        })}
                        className={`form-control base-input ${errors.sheetCode ? 'is-invalid' : ''}`}
                        placeholder='Chapa' 
                        name="sheetCode"
                    >
                        {selectSheets && selectSheets.length > 0 ? (
                            selectSheets.map(sheet => (
                                <option key={sheet.code} value={sheet.code}>
                                    {sheet.description}
                                </option>
                            ))
                        ) : (
                            <option value="" disabled>
                                Carregando opções...
                            </option>
                        )}
                    </select>
                    {errors.sheetCode && (
                        <div className='invalid-feedback d-block'>Campo obrigatório</div>
                    )}
                </div>
            </div>
            <div className="col-lg-6 crud-modal-half-container">
                <div className='margin-bottom-10'>
                    <label htmlFor="">Quantidade Líquida</label>
                    <input 
                        {...register("netQuantity", {
                        })}
                        type="text"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.netQuantity ? 'is-invalid' : ''}`}
                        placeholder="Quantidade Líquida"
                        name="netQuantity"
                    />
                    {errors.netQuantity && <div className='invalid-feedback d-block'>{errors.netQuantity.message}</div>}
                </div>
                <div className='margin-bottom-10'>
                    <label htmlFor="">Quantidade Bruta</label>
                    <input 
                        {...register("grossQuantity", {
                        })}
                        type="text"
                        inputMode="numeric"
                        className={`form-control base-input ${errors.grossQuantity ? 'is-invalid' : ''}`}
                        placeholder="Quantidade Bruta"
                        name="grossQuantity"
                    />
                    {errors.grossQuantity && <div className='invalid-feedback d-block'>{errors.grossQuantity.message}</div>}
                </div>
            </div>
            {errorMessage && <div className='invalid-feedback d-block'>{errorMessage}</div>}
        </div>
        <div className="crud-modal-buttons-container">
          <button onClick={onClose} className="btn cancel-button crud-modal-button">Cancelar</button>
          <button type="submit" className="btn btn-primary text-white crud-modal-button">Salvar</button>
        </div>
      </form>
    </Modal>
  );
};

export default UsedBackSheetModal;
