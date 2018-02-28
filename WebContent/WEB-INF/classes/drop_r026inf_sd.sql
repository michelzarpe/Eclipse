USE [Copia2]
GO
IF EXISTS(SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[vetorh].[r026inf_sd]') AND OBJECTPROPERTY(id, N'IsTrigger') = 1)
DROP TRIGGER [vetorh].[r026inf_sd]