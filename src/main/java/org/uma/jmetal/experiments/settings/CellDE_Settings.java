//  CellDE_Settings.java 
//
//  Authors:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.experiments.settings;

import org.uma.jmetal.core.Algorithm;
import org.uma.jmetal.core.Operator;
import org.uma.jmetal.experiments.Settings;
import org.uma.jmetal.metaheuristics.cellde.CellDE;
import org.uma.jmetal.operators.crossover.CrossoverFactory;
import org.uma.jmetal.operators.selection.SelectionFactory;
import org.uma.jmetal.problems.ProblemFactory;
import org.uma.jmetal.util.Configuration;
import org.uma.jmetal.util.JMetalException;

import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Settings class of algorithm CellDE
 */
public class CellDE_Settings extends Settings {
  private double cr_;
  private double f_;

  private int populationSize_;
  private int archiveSize_;
  private int maxEvaluations_;
  private int archiveFeedback_;

  /**
   * Constructor
   */
  public CellDE_Settings(String problemName) {
    super(problemName);

    Object[] problemParams = {"Real"};
    try {
      problem_ = (new ProblemFactory()).getProblem(problemName_, problemParams);
    } catch (JMetalException e) {
      Configuration.logger_.log(Level.SEVERE, "Unable to get problem", e);
      throw new JMetalException(e);
    }

    // Default experiments.settings
    cr_ = 0.5;
    f_ = 0.5;

    populationSize_ = 100;
    archiveSize_ = 100;
    maxEvaluations_ = 25000;
    archiveFeedback_ = 20;
  }

  /**
   * Configure the algorithm with the specified parameter experiments.settings
   *
   * @return an algorithm object
   * @throws org.uma.jmetal.util.JMetalException
   */
  public Algorithm configure() throws JMetalException {
    Algorithm algorithm;
    Operator selection;
    Operator crossover;

    // Creating the problem
    Object[] problemParams = {"Real"};
    problem_ = (new ProblemFactory()).getProblem(problemName_, problemParams);
    algorithm = new CellDE();
    algorithm.setProblem(problem_);

    // Algorithm parameters
    algorithm.setInputParameter("populationSize", populationSize_);
    algorithm.setInputParameter("archiveSize", archiveSize_);
    algorithm.setInputParameter("maxEvaluations", maxEvaluations_);
    algorithm.setInputParameter("archiveFeedBack", archiveFeedback_);

    // Crossover operator 
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("CR", cr_);
    parameters.put("F", f_);
    crossover = CrossoverFactory.getCrossoverOperator("DifferentialEvolutionCrossover", parameters);

    // Add the operators to the algorithm
    parameters = null;
    selection = SelectionFactory.getSelectionOperator("BinaryTournament", parameters);

    algorithm.addOperator("crossover", crossover);
    algorithm.addOperator("selection", selection);

    return algorithm;
  }

  /**
   * Configure CellDE with user-defined parameter experiments.settings
   *
   * @return A CellDE algorithm object
   */
  @Override
  public Algorithm configure(Properties configuration) throws JMetalException {
    populationSize_ = Integer
      .parseInt(configuration.getProperty("populationSize", String.valueOf(populationSize_)));
    maxEvaluations_ = Integer
      .parseInt(configuration.getProperty("maxEvaluations", String.valueOf(maxEvaluations_)));
    archiveSize_ =
      Integer.parseInt(configuration.getProperty("archiveSize", String.valueOf(archiveSize_)));
    archiveFeedback_ = Integer
      .parseInt(configuration.getProperty("archiveFeedback", String.valueOf(archiveFeedback_)));

    cr_ = Double.parseDouble(configuration.getProperty("CR", String.valueOf(cr_)));
    f_ = Double.parseDouble(configuration.getProperty("F", String.valueOf(f_)));

    return configure();
  }
} 