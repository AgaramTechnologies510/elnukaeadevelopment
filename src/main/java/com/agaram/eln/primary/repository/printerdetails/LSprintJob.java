package com.agaram.eln.primary.repository.printerdetails;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.PrinterSettings.PrintJob;

public interface LSprintJob extends JpaRepository <PrintJob, Long> {

}
