///////////////////////////////////////////////////////////////////////////////
// shadermanager.cpp
// ============
// manage the loading and rendering of 3D scenes
//
//  AUTHOR: Brian Battersby - SNHU Instructor / Computer Science
//	Created for CS-330-Computational Graphics and Visualization, Nov. 1st, 2023
///////////////////////////////////////////////////////////////////////////////

#include "SceneManager.h"

#ifndef STB_IMAGE_IMPLEMENTATION
#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
#endif

#include <glm/gtx/transform.hpp>

// declaration of global variables
namespace
{
	const char* g_ModelName = "model";
	const char* g_ColorValueName = "objectColor";
	const char* g_TextureValueName = "objectTexture";
	const char* g_UseTextureName = "bUseTexture";
	const char* g_UseLightingName = "bUseLighting";
}

/***********************************************************
 *  SceneManager()
 *
 *  The constructor for the class
 ***********************************************************/
SceneManager::SceneManager(ShaderManager *pShaderManager)
{
	m_pShaderManager = pShaderManager;
	m_basicMeshes = new ShapeMeshes();
}

/***********************************************************
 *  ~SceneManager()
 *
 *  The destructor for the class
 ***********************************************************/
SceneManager::~SceneManager()
{
	m_pShaderManager = NULL;
	delete m_basicMeshes;
	m_basicMeshes = NULL;
}

/***********************************************************
 *  SetTransformations()
 *
 *  This method is used for setting the transform buffer
 *  using the passed in transformation values.
 ***********************************************************/
void SceneManager::SetTransformations(
	glm::vec3 scaleXYZ,
	float XrotationDegrees,
	float YrotationDegrees,
	float ZrotationDegrees,
	glm::vec3 positionXYZ)
{
	// variables for this method
	glm::mat4 modelView;
	glm::mat4 scale;
	glm::mat4 rotationX;
	glm::mat4 rotationY;
	glm::mat4 rotationZ;
	glm::mat4 translation;

	// set the scale value in the transform buffer
	scale = glm::scale(scaleXYZ);
	// set the rotation values in the transform buffer
	rotationX = glm::rotate(glm::radians(XrotationDegrees), glm::vec3(1.0f, 0.0f, 0.0f));
	rotationY = glm::rotate(glm::radians(YrotationDegrees), glm::vec3(0.0f, 1.0f, 0.0f));
	rotationZ = glm::rotate(glm::radians(ZrotationDegrees), glm::vec3(0.0f, 0.0f, 1.0f));
	// set the translation value in the transform buffer
	translation = glm::translate(positionXYZ);

	// matrix math for calculating the final model matrix
	modelView = translation * rotationX * rotationY * rotationZ * scale;

	if (NULL != m_pShaderManager)
	{
		// pass the model matrix into the shader
		m_pShaderManager->setMat4Value(g_ModelName, modelView);
	}
}

/***********************************************************
 *  SetShaderColor()
 *
 *  This method is used for setting the passed in color
 *  into the shader for the next draw command
 ***********************************************************/
void SceneManager::SetShaderColor(
	float redColorValue,
	float greenColorValue,
	float blueColorValue,
	float alphaValue)
{
	// variables for this method
	glm::vec4 currentColor;

	currentColor.r = redColorValue;
	currentColor.g = greenColorValue;
	currentColor.b = blueColorValue;
	currentColor.a = alphaValue;

	if (NULL != m_pShaderManager)
	{
		// pass the color values into the shader
		m_pShaderManager->setIntValue(g_UseTextureName, false);
		m_pShaderManager->setVec4Value(g_ColorValueName, currentColor);
	}
}

/**************************************************************/
/*** STUDENTS CAN MODIFY the code in the methods BELOW for  ***/
/*** preparing and rendering their own 3D replicated scenes.***/
/*** Please refer to the code in the OpenGL sample project  ***/
/*** for assistance.                                        ***/
/**************************************************************/

/***********************************************************
 *  PrepareScene()
 *
 *  This method is used for preparing the 3D scene by loading
 *  the shapes, textures in memory to support the 3D scene 
 *  rendering
 ***********************************************************/
void SceneManager::PrepareScene()
{
	// only one instance of a particular mesh needs to be
	// loaded in memory no matter how many times it is drawn
	// in the rendered 3D scene

	m_basicMeshes->LoadPlaneMesh();
	m_basicMeshes->LoadCylinderMesh();
	m_basicMeshes->LoadSphereMesh();
	m_basicMeshes->LoadConeMesh();
	m_basicMeshes->LoadBoxMesh();
}

/***********************************************************
 *  LightScene()
 *
 *  This method is used for setting light in the 3D scene
 ***********************************************************/
void SceneManager::LightScene()
{
	// enable light in the shader
	m_pShaderManager->setBoolValue(g_UseLightingName, true);

	// set camera position
	m_pShaderManager->setVec3Value("viewPosition", glm::vec3(0.0f, 5.0f, 20.0f));

	// main light
	m_pShaderManager->setVec3Value("lightSources[0].position", glm::vec3(5.0f, 8.0f, 15.0f));
	m_pShaderManager->setVec3Value("lightSources[0].ambientColor", glm::vec3(0.3f, 0.3f, 0.3f));
	m_pShaderManager->setVec3Value("lightSources[0].diffuseColor", glm::vec3(0.8f, 0.8f, 0.8f));
	m_pShaderManager->setVec3Value("lightSources[0].specularColor", glm::vec3(0.1f, 0.1f, 0.1f));
	m_pShaderManager->setFloatValue("lightSources[0].focalStrength", 16.0f);
	m_pShaderManager->setFloatValue("lightSources[0].specularIntensity", 0.05f);

	// fill light 1
	m_pShaderManager->setVec3Value("lightSources[1].position", glm::vec3(-5.0f, 8.0f, 15.0f));
	m_pShaderManager->setVec3Value("lightSources[1].ambientColor", glm::vec3(0.2f, 0.2f, 0.2f));
	m_pShaderManager->setVec3Value("lightSources[1].diffuseColor", glm::vec3(0.8f, 0.8f, 0.8f));
	m_pShaderManager->setVec3Value("lightSources[1].specularColor", glm::vec3(0.0f, 0.0f, 0.0f));
	m_pShaderManager->setFloatValue("lightSources[1].focalStrength", 8.0f);
	m_pShaderManager->setFloatValue("lightSources[1].specularIntensity", 0.0f);

	// fill light 2
	m_pShaderManager->setVec3Value("lightSources[2].position", glm::vec3(0.0f, 20.0f, 5.0f));
	m_pShaderManager->setVec3Value("lightSources[2].ambientColor", glm::vec3(0.2f, 0.2f, 0.2f));
	m_pShaderManager->setVec3Value("lightSources[2].diffuseColor", glm::vec3(0.6f, 0.6f, 0.6f));
	m_pShaderManager->setVec3Value("lightSources[2].specularColor", glm::vec3(0.0f, 0.0f, 0.0f));
	m_pShaderManager->setFloatValue("lightSources[2].focalStrength", 8.0f);
	m_pShaderManager->setFloatValue("lightSources[2].specularIntensity", 0.0f);

	// fill light 3
	m_pShaderManager->setVec3Value("lightSources[3].position", glm::vec3(0.0f, 5.0f, 25.0f));
	m_pShaderManager->setVec3Value("lightSources[3].ambientColor", glm::vec3(0.3f, 0.3f, 0.3));
	m_pShaderManager->setVec3Value("lightSources[3].diffuseColor", glm::vec3(1.0f, 1.0f, 1.0f));
	m_pShaderManager->setVec3Value("lightSources[3].specularColor", glm::vec3(0.0f, 0.0f, 0.0f));
	m_pShaderManager->setFloatValue("lightSources[3].focalStrength", 8.0f);
	m_pShaderManager->setFloatValue("lightSources[3].specularIntensity", 0.0f);

	// fill light 4
	m_pShaderManager->setVec3Value("lightSources[2].position", glm::vec3(-15.0f, 8.0f, 5.0f));
m_pShaderManager->setVec3Value("lightSources[2].ambientColor", glm::vec3(0.2f, 0.2f, 0.2f));
m_pShaderManager->setVec3Value("lightSources[2].diffuseColor", glm::vec3(0.4f, 0.4f, 0.4f));
m_pShaderManager->setVec3Value("lightSources[2].specularColor", glm::vec3(0.0f, 0.0f, 0.0f));
m_pShaderManager->setFloatValue("lightSources[2].focalStrength", 8.0f);
m_pShaderManager->setFloatValue("lightSources[2].specularIntensity", 0.0f);

	// material properties
	m_pShaderManager->setVec3Value("material.ambientColor", glm::vec3(0.3f, 0.3f, 0.3f));
	m_pShaderManager->setFloatValue("material.ambientStrength", 0.4f);
	m_pShaderManager->setVec3Value("material.diffuseColor", glm::vec3(0.8f, 0.8f, 0.8f));
	m_pShaderManager->setVec3Value("material.specularColor", glm::vec3(0.1f, 0.1f, 0.1f));
	m_pShaderManager->setFloatValue("material.shininess", 8.0f);
}

/***********************************************************
 *  RenderScene()
 *
 *  This method is used for rendering the 3D scene by 
 *  transforming and drawing the basic 3D shapes
 ***********************************************************/
void SceneManager::RenderScene()
{
	// call LightScene
	LightScene();

	// declare the variables for the transformations
	glm::vec3 scaleXYZ;
	float XrotationDegrees = 0.0f;
	float YrotationDegrees = 0.0f;
	float ZrotationDegrees = 0.0f;
	glm::vec3 positionXYZ;

	/*** Set needed transformations before drawing the basic mesh.  ***/
	/*** This same ordering of code should be used for transforming ***/
	/*** and drawing all the basic 3D shapes.						***/
	/******************************************************************/
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(20.0f, 1.0f, 10.0f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 0.0f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	// update alpha value from 0.706 to 1.0 for solid color
	SetShaderColor(0.275, 0.510, 1, 1.0);

	// set lighting 
	m_pShaderManager->setIntValue(g_UseLightingName, true);

	// draw the mesh with transformation values
	m_basicMeshes->DrawPlaneMesh();
	/****************************************************************/

	/*** Set needed transformations before drawing the basic mesh.  ***/
	/*** This same ordering of code should be used for transforming ***/
	/*** and drawing all the basic 3D shapes.						***/
	/******************************************************************/

	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(20.0f, 1.0f, 10.0f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 90.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 9.0f, -10.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	// update alpha value from 0.706 to 1.0 for solid color
	SetShaderColor(0.275, 0.510, 1, 1.0);

	// set lighting
	m_pShaderManager->setIntValue(g_UseLightingName, true);

	// draw the mesh with transformation values
	m_basicMeshes->DrawPlaneMesh();
	/****************************************************************/

	// Left Cylinder 
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(3.0f, 3.0f, 3.0f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-6.0f, 0.0f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	// update alpha value from 0.439 to 1.0 for solid color
	SetShaderColor(0.098, 0.098, 1, 1.0);

	// set lighting
	m_pShaderManager->setIntValue(g_UseLightingName, true);

	// draw the mesh with transformation values
	m_basicMeshes->DrawCylinderMesh();
	/****************************************************************/

	// Middle Cylinder 
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(3.0f, 6.0f, 3.0f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 0.0f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	// update alpha value from 0.439 to 1.0 for solid color
	SetShaderColor(0.098, 0.098, 1, 1.0);

	// set lighting
	m_pShaderManager->setIntValue(g_UseLightingName, true);

	// draw the mesh with transformation values
	m_basicMeshes->DrawCylinderMesh();
	/****************************************************************/

	// Right Cylinder 
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(3.0f, 4.f, 3.0f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(6.0f, 0.0f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	// update alpha value from 0.439 to 1.0 for solid color
	SetShaderColor(0.098, 0.098, 1, 1.0);

	// set lighting
	m_pShaderManager->setIntValue(g_UseLightingName, true);

	// draw the mesh with transformation values
	m_basicMeshes->DrawCylinderMesh();
	/****************************************************************/

	// Sphere
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(2.3f, 2.3f, 2.3f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(-6.0f, 5.1f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	// update alpha value from 0.827 to 1.0 for solid color
	SetShaderColor(0.580, 0.000, 1, 1.0);

	// set lighting
	m_pShaderManager->setIntValue(g_UseLightingName, true);

	// draw the mesh with transformation values
	m_basicMeshes->DrawSphereMesh();
	/****************************************************************/

	// Cone
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(1.8f, 4.8f, 1.5f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 0.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(0.0f, 6.2f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1, 1, 0.0, 1.0);

	// set lighting
	m_pShaderManager->setIntValue(g_UseLightingName, true);

	// draw the mesh with transformation values
	m_basicMeshes->DrawConeMesh();
	/****************************************************************/

	// Box
	// set the XYZ scale for the mesh
	scaleXYZ = glm::vec3(2.8f, 2.8f, 2.8f);

	// set the XYZ rotation for the mesh
	XrotationDegrees = 0.0f;
	YrotationDegrees = 45.0f;
	ZrotationDegrees = 0.0f;

	// set the XYZ position for the mesh
	positionXYZ = glm::vec3(6.0f, 5.5f, 0.0f);

	// set the transformations into memory to be used on the drawn meshes
	SetTransformations(
		scaleXYZ,
		XrotationDegrees,
		YrotationDegrees,
		ZrotationDegrees,
		positionXYZ);

	// set the color values into the shader
	SetShaderColor(1.0, 0.0, 0, 1.0);

	// set lighting
	m_pShaderManager->setIntValue(g_UseLightingName, true);

	// draw the mesh with transformation values
	m_basicMeshes->DrawBoxMesh();
	/****************************************************************/
}