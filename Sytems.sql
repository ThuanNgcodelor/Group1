USE [master]
GO
/****** Object:  Database [Sytems]    Script Date: 11/6/2024 1:50:04 PM ******/
CREATE DATABASE [Sytems]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Sytems', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\Sytems.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Sytems_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\Sytems_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [Sytems] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Sytems].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Sytems] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Sytems] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Sytems] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Sytems] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Sytems] SET ARITHABORT OFF 
GO
ALTER DATABASE [Sytems] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Sytems] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Sytems] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Sytems] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Sytems] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Sytems] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Sytems] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Sytems] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Sytems] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Sytems] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Sytems] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Sytems] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Sytems] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Sytems] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Sytems] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Sytems] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Sytems] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Sytems] SET RECOVERY FULL 
GO
ALTER DATABASE [Sytems] SET  MULTI_USER 
GO
ALTER DATABASE [Sytems] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Sytems] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Sytems] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Sytems] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Sytems] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Sytems] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Sytems', N'ON'
GO
ALTER DATABASE [Sytems] SET QUERY_STORE = ON
GO
ALTER DATABASE [Sytems] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Sytems]
GO
/****** Object:  Table [dbo].[accounts]    Script Date: 11/6/2024 1:50:04 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[accounts](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fullname] [varchar](55) NULL,
	[email] [varchar](255) NULL,
	[password] [varchar](55) NULL,
	[phone] [int] NULL,
	[address] [varchar](255) NULL,
	[role] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[area]    Script Date: 11/6/2024 1:50:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[area](
	[areaID] [int] IDENTITY(1,1) NOT NULL,
	[areaName] [varchar](55) NULL,
PRIMARY KEY CLUSTERED 
(
	[areaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[bill]    Script Date: 11/6/2024 1:50:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[bill](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[date] [datetime] NULL,
	[total] [int] NULL,
	[status] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[category]    Script Date: 11/6/2024 1:50:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category](
	[categoryID] [int] IDENTITY(1,1) NOT NULL,
	[categoryName] [varchar](55) NULL,
PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[chip]    Script Date: 11/6/2024 1:50:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chip](
	[chipID] [int] IDENTITY(1,1) NOT NULL,
	[chipName] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[chipID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 11/6/2024 1:50:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[productName] [varchar](55) NULL,
	[price] [int] NULL,
	[quantity] [int] NULL,
	[order_details] [int] NULL,
	[billID] [int] NULL,
	[productID] [int] NULL,
	[status] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[products]    Script Date: 11/6/2024 1:50:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[products](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[productName] [varchar](55) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[image] [varchar](255) NULL,
	[categoryID] [int] NULL,
	[areaID] [int] NULL,
	[chipID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[accounts] ON 

INSERT [dbo].[accounts] ([id], [fullname], [email], [password], [phone], [address], [role]) VALUES (1, N'Tan Dinh', N'tandinh@gmail.com', N'123456', 388509046, N'hihi', 0)
INSERT [dbo].[accounts] ([id], [fullname], [email], [password], [phone], [address], [role]) VALUES (6, N'David Nguyen', N'DavidNguyen@gmail.com', N'12312312312312', 388509046, N'hihi', 0)
INSERT [dbo].[accounts] ([id], [fullname], [email], [password], [phone], [address], [role]) VALUES (8, N'David Nguyendâsdas', N'DavidNguyen@gmail.com', N'12312312312312', 388509046, N'hihi', 0)
INSERT [dbo].[accounts] ([id], [fullname], [email], [password], [phone], [address], [role]) VALUES (9, N'THu?n', N'DavidNguyen@gmail.com', N'12312312312312', 388509046, N'hihi', 0)
INSERT [dbo].[accounts] ([id], [fullname], [email], [password], [phone], [address], [role]) VALUES (10, N'THu?n', N'DavidNguyen@gmail.com', N'12312312312312', 388509046, N'hihi', 1)
INSERT [dbo].[accounts] ([id], [fullname], [email], [password], [phone], [address], [role]) VALUES (11, N'THu?n', N'davidNguyen@gmail.com', N'123456', 388509046, N'hihi', 1)
SET IDENTITY_INSERT [dbo].[accounts] OFF
GO
SET IDENTITY_INSERT [dbo].[area] ON 

INSERT [dbo].[area] ([areaID], [areaName]) VALUES (2, N'HIhi')
SET IDENTITY_INSERT [dbo].[area] OFF
GO
SET IDENTITY_INSERT [dbo].[bill] ON 

INSERT [dbo].[bill] ([id], [date], [total], [status]) VALUES (1, CAST(N'2024-11-01T21:03:47.880' AS DateTime), 527934, 0)
INSERT [dbo].[bill] ([id], [date], [total], [status]) VALUES (2, CAST(N'2024-11-02T15:14:49.750' AS DateTime), 1055868, 0)
INSERT [dbo].[bill] ([id], [date], [total], [status]) VALUES (3, CAST(N'2024-11-02T22:25:49.397' AS DateTime), 240000, 0)
SET IDENTITY_INSERT [dbo].[bill] OFF
GO
SET IDENTITY_INSERT [dbo].[category] ON 

INSERT [dbo].[category] ([categoryID], [categoryName]) VALUES (1, N'Hahaaasd')
SET IDENTITY_INSERT [dbo].[category] OFF
GO
SET IDENTITY_INSERT [dbo].[chip] ON 

INSERT [dbo].[chip] ([chipID], [chipName]) VALUES (1, N'hihi')
INSERT [dbo].[chip] ([chipID], [chipName]) VALUES (2, N'hihaaaaa')
SET IDENTITY_INSERT [dbo].[chip] OFF
GO
SET IDENTITY_INSERT [dbo].[orders] ON 

INSERT [dbo].[orders] ([id], [productName], [price], [quantity], [order_details], [billID], [productID], [status]) VALUES (1, N'ghfjjgjh', 527934, 6, 0, 1, 7, 0)
INSERT [dbo].[orders] ([id], [productName], [price], [quantity], [order_details], [billID], [productID], [status]) VALUES (5, N'ghfjjgjh', 263967, 3, 0, 2, 7, 0)
INSERT [dbo].[orders] ([id], [productName], [price], [quantity], [order_details], [billID], [productID], [status]) VALUES (6, N'ghfjjgjh', 263967, 3, 0, 2, 9, 0)
INSERT [dbo].[orders] ([id], [productName], [price], [quantity], [order_details], [billID], [productID], [status]) VALUES (7, N'ghfjjgjh', 175978, 2, 0, 2, 10, 0)
INSERT [dbo].[orders] ([id], [productName], [price], [quantity], [order_details], [billID], [productID], [status]) VALUES (8, N'ghfjjgjh', 175978, 2, 0, 2, 12, 0)
INSERT [dbo].[orders] ([id], [productName], [price], [quantity], [order_details], [billID], [productID], [status]) VALUES (9, N'ghfjjgjh', 175978, 2, 0, 2, 11, 0)
INSERT [dbo].[orders] ([id], [productName], [price], [quantity], [order_details], [billID], [productID], [status]) VALUES (11, N'ghfjjgjh', 240000, 3, 0, 3, 9, 0)
SET IDENTITY_INSERT [dbo].[orders] OFF
GO
SET IDENTITY_INSERT [dbo].[products] ON 

INSERT [dbo].[products] ([id], [productName], [price], [quantity], [image], [categoryID], [areaID], [chipID]) VALUES (7, N'ghfjjgjh', 90000, 68, N'C:\Users\nguye\Pictures\thankfefeback.jpg', 1, 2, 2)
INSERT [dbo].[products] ([id], [productName], [price], [quantity], [image], [categoryID], [areaID], [chipID]) VALUES (9, N'ghfjjgjh', 80000, 65, N'C:\Users\nguye\Pictures\thankfefeback.jpg', 1, 2, 1)
INSERT [dbo].[products] ([id], [productName], [price], [quantity], [image], [categoryID], [areaID], [chipID]) VALUES (10, N'ghfjjgjh', 70000, 68, N'C:\Users\nguye\Pictures\Untitled.png', 1, 2, 1)
INSERT [dbo].[products] ([id], [productName], [price], [quantity], [image], [categoryID], [areaID], [chipID]) VALUES (11, N'ghfjjgjh', 70000, 68, N'C:\Users\nguye\Pictures\Untitled.png', 1, 2, 1)
INSERT [dbo].[products] ([id], [productName], [price], [quantity], [image], [categoryID], [areaID], [chipID]) VALUES (12, N'ghfjjgjh', 89000, 68, N'C:\Users\nguye\Pictures\fefeback.jpg', 1, 2, 1)
SET IDENTITY_INSERT [dbo].[products] OFF
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [orders_orders__fk] FOREIGN KEY([billID])
REFERENCES [dbo].[bill] ([id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [orders_orders__fk]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [orders_orders__fk_2] FOREIGN KEY([productID])
REFERENCES [dbo].[products] ([id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [orders_orders__fk_2]
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD  CONSTRAINT [products_products__fk] FOREIGN KEY([areaID])
REFERENCES [dbo].[area] ([areaID])
GO
ALTER TABLE [dbo].[products] CHECK CONSTRAINT [products_products__fk]
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD  CONSTRAINT [products_products__fk_2] FOREIGN KEY([categoryID])
REFERENCES [dbo].[category] ([categoryID])
GO
ALTER TABLE [dbo].[products] CHECK CONSTRAINT [products_products__fk_2]
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD  CONSTRAINT [products_products__fk_3] FOREIGN KEY([chipID])
REFERENCES [dbo].[chip] ([chipID])
GO
ALTER TABLE [dbo].[products] CHECK CONSTRAINT [products_products__fk_3]
GO
USE [master]
GO
ALTER DATABASE [Sytems] SET  READ_WRITE 
GO
