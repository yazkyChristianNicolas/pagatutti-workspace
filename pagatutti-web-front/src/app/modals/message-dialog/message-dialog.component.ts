import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';


export interface MessageDialogData {
  message: string;
}

@Component({
  selector: 'message-dialog',
  templateUrl: './message-dialog.component.html',
  styleUrls: ['./message-dialog.component.scss']
})
export class MessageDialog {

  constructor(@Inject(MAT_DIALOG_DATA) public data: MessageDialogData) {}
  
}
